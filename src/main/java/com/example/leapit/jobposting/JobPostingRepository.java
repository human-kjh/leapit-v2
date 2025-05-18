package com.example.leapit.jobposting;

import com.example.leapit.common.enums.CareerLevel;
import com.example.leapit.common.enums.SortType;
import com.example.leapit.companyinfo.CompanyInfo;
import com.example.leapit.companyinfo.CompanyInfoResponse;
import com.example.leapit.jobposting.bookmark.JobPostingBookmark;
import com.example.leapit.jobposting.techstack.JobPostingTechStack;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class JobPostingRepository {
    private final EntityManager em;

    public List<JobPostingResponse.JobPostingDTO> findAllByFilter(
            Integer regionId,
            Integer subRegionId,
            CareerLevel career,
            String techStackCode,
            String positionLabel,
            SortType sortType,
            Integer sessionUserId
    ) {

        LocalDate today = LocalDate.now();

        StringBuilder jpql = new StringBuilder(
                "SELECT jp, jpts, ci " +
                        "FROM JobPosting jp " +
                        "LEFT JOIN JobPostingTechStack jpts ON jp.id = jpts.jobPosting.id " +
                        "LEFT JOIN CompanyInfo ci ON jp.user.id = ci.user.id " +
                        "WHERE jp.deadline >= :today"
        );

        // 지역 필터링
        if (regionId != null) {
            jpql.append(" AND jp.addressRegionId = :regionId");
        }

        // 서브지역 필터링
        if (subRegionId != null) {
            jpql.append(" AND jp.addressSubRegionId = :subRegionId");
        }

        // 경력 필터링
        if (career != null) {
            jpql.append(" AND :careerValue >= jp.minCareerLevel AND :careerValue <= jp.maxCareerLevel");
        }

        // 직무(포지션) 필터링
        if (positionLabel != null) {
            jpql.append(" AND jp.positionType = :positionLabel");
        }

        // 기술 스택 필터링
        if (techStackCode != null) {
            jpql.append(" AND EXISTS (" +
                    "SELECT 1 FROM JobPostingTechStack jpts2 " +
                    "WHERE jpts2.jobPosting.id = jp.id " +
                    "AND jpts2.techStack.code = :techStackCode" +
                    ")");
        }

        // 정렬
        if (sortType == SortType.POPULAR) {
            jpql.append(" ORDER BY jp.viewCount DESC");
        } else if (sortType == SortType.LATEST) {
            jpql.append(" ORDER BY jp.createdAt DESC");
        }

        Query query = em.createQuery(jpql.toString(), Object[].class);
        query.setParameter("today", today);

        if (regionId != null) query.setParameter("regionId", regionId);
        if (subRegionId != null) query.setParameter("subRegionId", subRegionId);
        if (career != null) query.setParameter("careerValue", career.getValue());
        if (techStackCode != null) query.setParameter("techStackCode", techStackCode);
        if (positionLabel != null) query.setParameter("positionLabel", positionLabel);

        List<Object[]> results = query.getResultList();
        List<JobPostingResponse.JobPostingDTO> dtos = new ArrayList<>();

        Integer lastJobPostingId = null;
        JobPostingResponse.JobPostingDTO currentDTO = null;

        for (Object[] result : results) {
            JobPosting jobPosting = (JobPosting) result[0];
            JobPostingTechStack techStack = (JobPostingTechStack) result[1];
            CompanyInfo companyInfo = (CompanyInfo) result[2];

            boolean isBookmarked = isBookmarked(jobPosting.getId(), sessionUserId);

            if (!jobPosting.getId().equals(lastJobPostingId)) {
                String address = companyInfo != null ? companyInfo.getAddress() : "주소 없음";
                String image = companyInfo != null ? companyInfo.getImage() : "이미지 없음";
                String companyName = companyInfo != null ? companyInfo.getCompanyName() : "회사명 없음";

                List<JobPostingTechStack> techStacks = new ArrayList<>();
                if (techStack != null) {
                    techStacks.add(techStack);
                }

                // JobPostingDTO 생성 시, isBookmarked 값 전달
                currentDTO = new JobPostingResponse.JobPostingDTO(
                        jobPosting, techStacks, address, image, companyName, isBookmarked
                );
                dtos.add(currentDTO);

                lastJobPostingId = jobPosting.getId();
            } else {
                if (techStack != null && currentDTO != null) {
                    currentDTO.getTechStacks().add(new CompanyInfoResponse.TechStackDTO(
                            techStack.getTechStack().getCode()
                    ));
                }
            }
        }
        return dtos;
    }

    // 북마크 확인 메서드
    public boolean isBookmarked(Integer jobPostingId, Integer sessionUserId) {
        JobPostingBookmark bookmark = em.createQuery(
                        "SELECT b FROM JobPostingBookmark b WHERE b.jobPosting.id = :jobPostingId AND b.user.id = :sessionUserId",
                        JobPostingBookmark.class)
                .setParameter("jobPostingId", jobPostingId)
                .setParameter("sessionUserId", sessionUserId)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
        return bookmark != null;
    }
}

