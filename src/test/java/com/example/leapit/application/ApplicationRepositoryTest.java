package com.example.leapit.application;

import com.example.leapit.common.enums.BookmarkStatus;
import com.example.leapit.common.enums.PassStatus;
import com.example.leapit.common.enums.ViewStatus;
import jakarta.persistence.EntityManager;
import com.example.leapit.companyinfo.CompanyInfoRepository;
import jdk.swing.interop.SwingInterOpUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(ApplicationRepository.class)
@DataJpaTest
public class ApplicationRepositoryTest {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private EntityManager em;

    // 지원받은 이력서 목록 조회
    @Test
    public void find_all_applicants_by_filter() {
        // given
        Integer companyUserId = 7;
        Integer jobPostingId = null;
        PassStatus passStatus = null;
        ViewStatus viewStatus = null;
        BookmarkStatus bookmarkStatus = null;

        // when
        List<ApplicationResponse.ApplicantListDTO> result =
                applicationRepository.findAllApplicantsByFilter(
                        companyUserId,
                        jobPostingId,
                        passStatus,
                        viewStatus,
                        bookmarkStatus
                );

        // eye
        System.out.println("===================================================");
        for (ApplicationResponse.ApplicantListDTO dto : result) {
            System.out.println("지원자 ID: " + dto.getApplicationId());
            System.out.println("이력서 ID: " + dto.getResumeId());
            System.out.println("지원자명: " + dto.getApplicantName());
            System.out.println("공고명: " + dto.getJobTitle());
            System.out.println("지원일: " + dto.getAppliedDate());
            System.out.println("열람 상태: " + dto.getViewStatus());
            System.out.println("스크랩 상태: " + dto.getBookmarkStatus());
            System.out.println("===================================================");
        }
    }

    @Test
    public void findAllByResumeId_test_existingData() {
        // given
        Integer resumeId = 2;

        // when
        List<Application> applications = applicationRepository.findAllByResumeId(resumeId);

        // then
        System.out.println("조회된 지원서 수: " + applications.size());
        for (Application app : applications) {
            System.out.println("지원서 ID: " + app.getId() + ", 이력서 ID: " + app.getResume().getId());
        }
    }

    @Test
    public void find_status_by_user_id_test() {
        // given
        Integer userId = 1;

        // when
        ApplicationResponse.StatusDTO dto = applicationRepository.findStatusByUserId(userId);

        // eye
        System.out.println("=========통계 테스트=========");
        System.out.println("총 지원 수: " + dto.getTotal());
        System.out.println("합격 수: " + dto.getPassed());
        System.out.println("불합격 수: " + dto.getFailed());
        System.out.println("=========== 끝 ===========");
    }

    @Test
    public void find_items_by_user_id_test() {
        // given
        Integer userId = 1;

        // when
        List<ApplicationResponse.MyPageDTO.ItemDTO> applications = applicationRepository.findItemsByUserId(userId);

        // eye
        System.out.println("=========지원현황 테스트=========");
        for (ApplicationResponse.MyPageDTO.ItemDTO dto : applications) {
            System.out.println("회사명: " + dto.getCompanyName());
            System.out.println("공고 제목: " + dto.getJobTitle());
            System.out.println("지원일: " + dto.getAppliedDate());
            System.out.println("이력서 ID: " + dto.getResumeId());
            System.out.println("공고 ID: " + dto.getJobPostingId());
            System.out.println("결과: " + dto.getResult());
            System.out.println("=========== 끝 ===========");
        }
    }
}
