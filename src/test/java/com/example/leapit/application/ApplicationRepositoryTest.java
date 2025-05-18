package com.example.leapit.application;

import com.example.leapit.common.enums.BookmarkStatus;
import com.example.leapit.common.enums.PassStatus;
import com.example.leapit.common.enums.ViewStatus;
import jakarta.persistence.EntityManager;
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
}
