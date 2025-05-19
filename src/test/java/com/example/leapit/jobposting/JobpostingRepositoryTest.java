package com.example.leapit.jobposting;

import com.example.leapit.companyinfo.CompanyInfo;
import com.example.leapit.companyinfo.CompanyInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

@Import({JobPostingRepository.class, CompanyInfoRepository.class})
@DataJpaTest
public class JobpostingRepositoryTest {

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Autowired
    private CompanyInfoRepository companyInfoRepository;

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
            System.out.println("최소 경력: " + jobPosting.getMinCareerLevel());
            System.out.println("최대 경력: " + jobPosting.getMaxCareerLevel());
            System.out.println("학력: " + jobPosting.getEducationLevel());
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

            // then
            System.out.println("======== 채용공고 상세보기 (구직자) ========");
            // eye
            System.out.println("제목: " + jobPosting.getTitle());
            System.out.println("직무: " + jobPosting.getPositionType());
            System.out.println("경력: " + jobPosting.getMinCareerLevel().label + " ~ " + jobPosting.getMaxCareerLevel().label);
            System.out.println("학력: " + jobPosting.getEducationLevel());
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

}
