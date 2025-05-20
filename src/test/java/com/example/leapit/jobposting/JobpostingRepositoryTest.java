package com.example.leapit.jobposting;

import com.example.leapit._core.error.ex.ExceptionApi404;
import com.example.leapit.common.enums.CareerLevel;
import com.example.leapit.common.enums.EducationLevel;
import com.example.leapit.companyinfo.CompanyInfo;
import com.example.leapit.companyinfo.CompanyInfoRepository;
import com.example.leapit.jobposting.techstack.JobPostingTechStack;
import com.example.leapit.user.User;
import com.example.leapit.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.*;

@Import({JobPostingRepository.class, CompanyInfoRepository.class, UserRepository.class})
@DataJpaTest
public class JobpostingRepositoryTest {

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void education_level_enum() {
        // given
        Integer id = 1;

        // when
        Optional<JobPosting> optional = jobPostingRepository.findById(id);

        // eye
        if (optional.isPresent()) {
            JobPosting jp = optional.get();
            System.out.println("===== educationLevel 테스트 =====");
            System.out.println("Enum name(): " + jp.getEducationLevel());         // → BACHELOR
            System.out.println("label: " + jp.getEducationLevel().label);         // → 학사
            System.out.println("=================================");
        }
    }

    @Test
    public void save_job_posting() {
        // given
        Integer userId = 6;

        User userPS = userRepository.findById(userId)
                .orElseThrow(() -> new ExceptionApi404("유저를 찾을 수 없습니다"));

        JobPosting jobPosting = JobPosting.builder()
                .user(userPS)
                .title("백엔드 개발자 모집")
                .positionType("백엔드")
                .minCareerLevel(CareerLevel.YEAR_2)
                .maxCareerLevel(CareerLevel.YEAR_5)
                .educationLevel(EducationLevel.BACHELOR)
                .addressRegionId(1)
                .addressSubRegionId(1)
                .addressDetail("테헤란로 123")
                .serviceIntro("우리 회사는 AI 기반의 솔루션을 제공합니다.")
                .deadline(LocalDate.of(2025, 6, 30))
                .responsibility("Spring Boot 기반 API 개발 및 운영")
                .qualification("Java 및 Spring에 대한 이해, 2년 이상 경력")
                .preference("AWS 경험자 우대")
                .benefit("자율 출퇴근, 점심 식대 지원")
                .additionalInfo("포트폴리오 제출 필수")
                .build();

        jobPosting.getJobPostingTechStacks().add(JobPostingTechStack.builder()
                .techStack("Java")
                .jobPosting(jobPosting)
                .build());
        jobPosting.getJobPostingTechStacks().add(JobPostingTechStack.builder()
                .techStack("Spring Boot")
                .jobPosting(jobPosting)
                .build());
        jobPosting.getJobPostingTechStacks().add(JobPostingTechStack.builder()
                .techStack("Python")
                .jobPosting(jobPosting)
                .build());

        // when
        JobPosting saved = jobPostingRepository.save(jobPosting);

        // eye
        System.out.println("============== 채용공고 저장 결과 ==============");
        System.out.println("ID: " + saved.getId());
        System.out.println("제목: " + saved.getTitle());
        System.out.println("직무: " + saved.getPositionType());
        System.out.println("경력: " + saved.getMinCareerLevel().label + " ~ " + saved.getMaxCareerLevel().label);
        System.out.println("학력: " + saved.getEducationLevel().label);
        System.out.println("주소: " + saved.getAddressRegionId() + " / " + saved.getAddressSubRegionId());
        System.out.println("상세주소: " + saved.getAddressDetail());
        System.out.println("서비스 소개: " + saved.getServiceIntro());
        System.out.println("마감일: " + saved.getDeadline());
        System.out.println("담당업무: " + saved.getResponsibility());
        System.out.println("자격요건: " + saved.getQualification());
        System.out.println("우대사항: " + saved.getPreference());
        System.out.println("복지: " + saved.getBenefit());
        System.out.println("추가정보: " + saved.getAdditionalInfo());
        System.out.print("기술스택: ");
        saved.getJobPostingTechStacks().forEach(stack -> System.out.print(stack.getTechStack() + " "));
        System.out.println("\n===============================================");
    }

    @Test
    public void job_posting_delete_test() {
        // given
        Integer jobPostingId = 1;

        // eye : 삭제 전
        Optional<JobPosting> before = jobPostingRepository.findById(jobPostingId);
        System.out.println("삭제 전: " + before);

        // when
        jobPostingRepository.deleteById(jobPostingId);

        // eye : 삭제 후
        Optional<JobPosting> after = jobPostingRepository.findById(jobPostingId);
        System.out.println("삭제 후: " + after); // Optional.empty
    }


    @Test
    public void company_job_posting_detail_test() {
        // given
        Optional<JobPosting> optionalJobPosting = jobPostingRepository.findById(1);

        // when
        if (optionalJobPosting.isPresent()) {
            JobPosting jobPosting = optionalJobPosting.get();
            // eye
            System.out.println("========채용공고 상세보기 결과========");
            System.out.println("ID: " + jobPosting.getId());
            System.out.println("제목: " + jobPosting.getTitle());
            System.out.println("직무: " + jobPosting.getPositionType());
            System.out.println("최소 경력: " + jobPosting.getMinCareerLevel().label);
            System.out.println("최대 경력: " + jobPosting.getMaxCareerLevel().label);
            System.out.println("학력: " + jobPosting.getEducationLevel().label);
            System.out.println("지역 ID: " + jobPosting.getAddressRegionId());
            System.out.println("서브지역 ID: " + jobPosting.getAddressSubRegionId());
            System.out.println("상세주소: " + jobPosting.getAddressDetail());
            System.out.println("서비스 소개: " + jobPosting.getServiceIntro());
            System.out.println("마감일: " + jobPosting.getDeadline());
            System.out.println("담당업무: " + jobPosting.getResponsibility());
            System.out.println("자격요건: " + jobPosting.getQualification());
            System.out.println("우대사항: " + jobPosting.getPreference());
            System.out.println("복지: " + jobPosting.getBenefit());
            System.out.println("추가정보: " + jobPosting.getAdditionalInfo());
            System.out.println("조회수: " + jobPosting.getViewCount());
            System.out.println("작성일: " + jobPosting.getCreatedAt());

            System.out.print("기술스택: ");
            jobPosting.getJobPostingTechStacks().forEach(stack -> System.out.print(stack.getTechStack() + " "));
            System.out.println();
            System.out.println("========채용공고 상세보기 끝========");
        }
    }

    @Test
    public void personal_job_posting_detail_test() {
        // given
        Optional<JobPosting> optionalJobPosting = jobPostingRepository.findById(1);

        // when
        if (optionalJobPosting.isPresent()) {
            JobPosting jobPosting = optionalJobPosting.get();

            Integer companyUserId = jobPosting.getUser().getId();
            Optional<CompanyInfo> optionalCompanyInfo = companyInfoRepository.findByUserId(companyUserId);

            // eye
            System.out.println("======== 채용공고 상세보기 (구직자) ========");
            System.out.println("제목: " + jobPosting.getTitle());
            System.out.println("직무: " + jobPosting.getPositionType());
            System.out.println("경력: " + jobPosting.getMinCareerLevel().label + " ~ " + jobPosting.getMaxCareerLevel().label);
            System.out.println("학력: " + jobPosting.getEducationLevel().label);
            System.out.println("지역: " + jobPosting.getAddressRegionId() + " - " + jobPosting.getAddressSubRegionId());
            System.out.println("마감일: " + jobPosting.getDeadline());
            System.out.println("조회수: " + jobPosting.getViewCount());
            System.out.print("기술스택: ");
            jobPosting.getJobPostingTechStacks().forEach(stack -> System.out.print(stack.getTechStack() + " "));
            System.out.println();

            if (optionalCompanyInfo.isPresent()) {
                CompanyInfo companyInfo = optionalCompanyInfo.get();
                System.out.println("---- 회사 정보 ----");
                System.out.println("회사명: " + companyInfo.getCompanyName());
                System.out.println("설립일: " + companyInfo.getEstablishmentDate());
                System.out.println("메인 서비스: " + companyInfo.getMainService());
                System.out.println("로고 이미지: " + companyInfo.getLogoImage());
            } else {
                System.out.println("회사 정보가 없습니다.");
            }

            System.out.println("======== 채용공고 상세보기 끝 ========");
        } else {
            System.out.println("채용공고를 찾을 수 없습니다.");
        }
    }

    @Test
    public void findTop8PopularJobPostingsAndTechStacks_test() {

        // when
        List<Object[]> result = jobPostingRepository.findTop8PopularJobPostingsAndTechStacks();

        // 공고 ID 기준으로 그룹핑
        Map<Integer, JobPosting> postingMap = new LinkedHashMap<>(); // 순서 유지
        Map<Integer, List<JobPostingTechStack>> stackMap = new HashMap<>();

        for (Object[] row : result) {
            JobPosting jp = (JobPosting) row[0];
            JobPostingTechStack stack = (JobPostingTechStack) row[1];

            // 공고 저장
            postingMap.putIfAbsent(jp.getId(), jp);

            // 기술스택 누적
            if (stack != null) {
                stackMap.computeIfAbsent(jp.getId(), k -> new ArrayList<>()).add(stack);
            }
        }

        // eye
        System.out.println("===== 인기 공고 + 기술스택 목록 =====");
        for (Map.Entry<Integer, JobPosting> entry : postingMap.entrySet()) {
            JobPosting jp = entry.getValue();
            System.out.println("공고명: " + jp.getTitle());

            List<JobPostingTechStack> techStacks = stackMap.getOrDefault(jp.getId(), new ArrayList<>());
            if (techStacks.isEmpty()) {
                System.out.println(" - 기술스택 없음");
            } else {
                for (JobPostingTechStack ts : techStacks) {
                    System.out.println(" - 기술스택: " + ts.getTechStack());
                }
            }
        }
    }

    @Test
    public void findTop8PopularJobPostings_test() {
        // when
        List<Integer> ids = jobPostingRepository.findTop8PopularJobPostings();

        // eye
        System.out.println("인기 공고 ID 목록: " + ids);
    }

    @Test
    public void findByRegion_test() {
        // given
        Integer jobPostingId = 1;

        // when
        String region = jobPostingRepository.findByRegion(jobPostingId);

        // eye
        System.out.println("시(region): " + region);
    }

    @Test
    public void findBySubRegion_test() {
        // given
        Integer jobPostingId = 1;

        // when
        String subRegion = jobPostingRepository.findBySubRegion(jobPostingId);

        // eye
        System.out.println("구(subRegion): " + subRegion);
    }

    @Test
    public void findTop3RecentJobPostings_test() {

        // when
        List<JobPosting> result = jobPostingRepository.findTop3RecentJobPostings();

        // eye
        System.out.println("===== 최신 공고 3개 =====");
        for (JobPosting jp : result) {
            System.out.println("공고명: " + jp.getTitle() + ", 등록일: " + jp.getCreatedAt());
        }
    }

}
