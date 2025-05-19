package com.example.leapit.jobposting;

import com.example.leapit._core.error.ex.ExceptionApi400;
import com.example.leapit._core.util.Base64Util;
import com.example.leapit.common.enums.CareerLevel;
import com.example.leapit.common.positiontype.PositionTypeRepository;
import com.example.leapit.common.region.Region;
import com.example.leapit.common.region.RegionRepository;
import com.example.leapit.common.region.SubRegion;
import com.example.leapit.common.techstack.TechStackRepository;
import com.example.leapit.companyinfo.CompanyInfo;
import com.example.leapit.companyinfo.CompanyInfoRepository;
import com.example.leapit.jobposting.bookmark.JobPostingBookmarkRepository;
import com.example.leapit.jobposting.techstack.JobPostingTechStack;
import com.example.leapit.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Service
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final TechStackRepository techStackRepository;
    private final RegionRepository regionRepository;
    private final PositionTypeRepository positionTypeRepository;
    private final CompanyInfoRepository companyInfoRepository;
    private final JobPostingBookmarkRepository jobPostingBookmarkRepository;


    // 채용공고 저장
    @Transactional
    public JobPostingResponse.DTO save(JobPostingRequest.SaveDTO reqDTO, User sessionUser) {
        // 1. 연관된 기술스택까지 포함된 JobPosting 생성
        JobPosting jobPosting = reqDTO.toEntity(sessionUser);

        // 2. 저장
        JobPosting jobPostingPS = jobPostingRepository.save(jobPosting);

        // 3. 응답용 DTO로 반환
        return new JobPostingResponse.DTO(jobPostingPS);
    }

    // 채용공고 저장 화면에 필요한 데이터 불러오기
    @Transactional
    public JobPostingResponse.SaveDTO getSaveForm() {
        // 1. 포지션 타입 코드 리스트 (예: ["백엔드", "프론트엔드"])
        List<String> positionTypes = positionTypeRepository.findAll();

        // 2. 기술 스택 코드 리스트 (예: ["Java", "React"])
        List<String> techStacks = techStackRepository.findAll();

        // 3. 전체 지역 조회
        List<Region> regions = regionRepository.findAllRegion();

        // 4. 커리어 레벨 (enum)
        List<CareerLevel> careerLevels = List.of(CareerLevel.values());

        // 5. Region + SubRegion DTO 변환
        List<JobPostingResponse.RegionDTO> regionDTOs = new ArrayList<>();
        for (Region region : regions) {
            List<SubRegion> subRegions = regionRepository.findAllSubRegionByRegionId(region.getId());
            JobPostingResponse.RegionDTO regionDTO = new JobPostingResponse.RegionDTO(region.getId(), region.getName(), subRegions);
            regionDTOs.add(regionDTO);
        }
        // 6. 최종 응답 DTO 생성
        return new JobPostingResponse.SaveDTO(positionTypes, techStacks, regionDTOs, careerLevels);
    }

    // 구직자 - 메인페이지 최신공고 3개
    public List<JobPostingResponse.MainDTO.MainRecentJobPostingDTO> getRecentPostings(Integer userId) {

        // 1. 등록일 기준으로 최신 공고 3개 조회
        List<JobPosting> recentPostings = jobPostingRepository.findTop3RecentJobPostings();

        // 2. 공고 순서를 체크하기 위한 인덱스 (첫 번째 공고는 isActive=true 표시용)
        AtomicInteger index = new AtomicInteger(0);

        // 3. 각 JobPosting을 MainRecentJobPostingDTO로 변환하여 리스트로 반환
        return recentPostings.stream()
                .map(jp -> {
                    int i = index.getAndIncrement();   // 현재 공고의 순번

                    // 3-1. 공고 작성자의 회사 정보 조회
                    CompanyInfo companyInfo = companyInfoRepository.findByUserId(jp.getUser().getId())
                            .orElseThrow(() -> new RuntimeException("회사 정보가 없습니다"));

                    // 3-2. DB에 있는 image를 Base64 문자열로 변환
                    String imageString = null;
                    try {
                        if (companyInfo.getImage() != null) {
                            byte[] imageBytes = Base64Util.readImageAsByteArray(companyInfo.getImage());
                            imageString = Base64Util.encodeAsString(imageBytes, "image/png");
                        }
                    } catch (Exception e) {
                        throw new ExceptionApi400("파일 읽기 실패");
                    }

                    // 3-3. 북마크 여부 확인 (로그인한 유저인 경우만 체크)
                    boolean isBookmarked = false;
                    if (userId != null) {
                        isBookmarked = jobPostingBookmarkRepository.findByUserIdAndJobPostingId(userId, jp.getId()) != null;
                    }

                    // 3-3. DTO 생성 (i == 0이면 첫 번째 공고 → isActive=true)
                    return new JobPostingResponse.MainDTO.MainRecentJobPostingDTO(jp, companyInfo, imageString, i == 0, isBookmarked);
                })
                .toList();
    }


    // 구직자 - 메인페이지 인기 공고 8개
    public List<JobPostingResponse.MainDTO.MainPopularJobPostingDTO> getPopularJobPostings(Integer userId) {
        // 1. 공고 + 기술스택을 (LEFT JOIN)으로 가져온 결과 (중복 포함된 row들)
        List<Object[]> results = jobPostingRepository.findTop8PopularJobPostingsAndTechStacks();

        // 2. 공고 ID를 기준으로 JobPosting과 TechStack을 분리해서 정리할 Map 생성
        Map<Integer, JobPosting> postingMap = new HashMap<>();
        Map<Integer, List<JobPostingTechStack>> stackMap = new HashMap<>();

        for (Object[] row : results) {
            JobPosting jp = (JobPosting) row[0];
            JobPostingTechStack stack = (JobPostingTechStack) row[1];

            // 공고는 중복 없이 1번만 저장
            postingMap.putIfAbsent(jp.getId(), jp);

            // 기술스택은 List로 누적
            if (stack != null) {
                stackMap.computeIfAbsent(jp.getId(), k -> new ArrayList<>()).add(stack);
            }
        }


        // 3. 원래 순서(top 8)를 유지하기 위해 ID만 따로 가져옴
        List<Integer> top8Ids = jobPostingRepository.top8();

        List<JobPostingResponse.MainDTO.MainPopularJobPostingDTO> popularList = new ArrayList<>();
        for (Integer id : top8Ids) {
            JobPosting jp = postingMap.get(id);
            if (jp == null) continue; // 혹시라도 누락될 경우 안전 처리

            // 4. 지역 정보 조합 (region + subRegion
            String region = jobPostingRepository.findByRegion(jp.getId());
            String subRegion = jobPostingRepository.findBySubRegion(jp.getId());
            String address = (region != null ? region : "") + " " + (subRegion != null ? subRegion : "");

            // 5. 기술스택 목록 꺼내기
            List<JobPostingTechStack> techStacks = stackMap.getOrDefault(jp.getId(), new ArrayList<>());

            // 6. 회사 정보 조회
            CompanyInfo companyInfo = companyInfoRepository.findByUserId(jp.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("회사 정보가 없습니다"));

            // 7. DB에 있는 image를 Base64 문자열로 변환
            String imageString = null;
            try {
                if (companyInfo.getImage() != null) {
                    byte[] imageBytes = Base64Util.readImageAsByteArray(companyInfo.getImage());
                    imageString = Base64Util.encodeAsString(imageBytes, "image/png");
                }
            } catch (Exception e) {
                throw new ExceptionApi400("파일 읽기 실패");
            }

            // 8. 북마크 여부 확인
            boolean isBookmarked = false;
            if (userId != null) {
                isBookmarked = jobPostingBookmarkRepository.findByUserIdAndJobPostingId(userId, jp.getId()) != null;
            }

            // 8. DTO로 변환 후 리스트에 추가
            popularList.add(new JobPostingResponse.MainDTO.MainPopularJobPostingDTO(
                    jp,
                    companyInfo.getCompanyName(),
                    imageString,
                    address,
                    techStacks,
                    isBookmarked
            ));
        }

        return popularList;
    }
}