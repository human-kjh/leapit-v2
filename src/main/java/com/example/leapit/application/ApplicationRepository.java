package com.example.leapit.application;

import com.example.leapit.common.enums.BookmarkStatus;
import com.example.leapit.common.enums.PassStatus;
import com.example.leapit.common.enums.ViewStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ApplicationRepository {
    private final EntityManager em;

    public List<Application> findAllByResumeId(Integer resumeId) {
        Query query = em.createQuery("Select a from Application a where a.resume.id = :resumeId");
        query.setParameter("resumeId", resumeId);
        return query.getResultList();
    }

    // 지원받은 이력서 목록 조회(필터 : 북마크여부, 열람여부, 합불여부)
    public List<ApplicationResponse.ApplicantListDTO> findAllApplicantsByFilter(
            Integer companyUserId,
            Integer jobPostingId,
            PassStatus passStatus,
            ViewStatus viewStatus,
            BookmarkStatus bookmarkStatus) {

        String sql = """
                    SELECT
                        att.id AS applicationId,
                        rt.id AS resumeId,
                        ut.name AS applicantName,
                        jpt.title AS jobTitle,
                        att.applied_date AS appliedDate,
                        CASE WHEN abt.id IS NOT NULL THEN TRUE ELSE FALSE END AS isBookmarked,
                        CASE
                            WHEN att.view_status = 'UNVIEWED' THEN '미열람'
                            WHEN att.view_status = 'VIEWED' AND att.pass_status = 'WAITING' THEN '열람'
                            WHEN att.pass_status = 'PASS' THEN '합격'
                            WHEN att.pass_status = 'FAIL' THEN '불합격'
                        END AS evaluationStatus,
                        att.view_status AS viewStatus
                    FROM APPLICATION_TB att
                    JOIN RESUME_TB rt ON att.resume_id = rt.id
                    JOIN USER_TB ut ON rt.user_id = ut.id
                    JOIN JOB_POSTING_TB jpt ON att.job_posting_id = jpt.id
                    JOIN USER_TB comut ON jpt.user_id = comut.id
                    LEFT JOIN APPLICATION_BOOKMARK_TB abt 
                        ON abt.application_id = att.id AND abt.user_id = :companyUserId
                    WHERE comut.id = :companyUserId
                """;

        if (jobPostingId != null) {
            sql += " AND jpt.id = :jobPostingId";
        }

        if (viewStatus != null) {
            sql += " AND att.view_status = '" + viewStatus.name() + "'";
        }

        if (passStatus != null) {
            sql += " AND att.pass_status = '" + passStatus.name() + "'";
        }

        if (bookmarkStatus != null) {
            if (bookmarkStatus == BookmarkStatus.BOOKMARKED) {
                sql += " AND abt.id IS NOT NULL";
            } else if (bookmarkStatus == BookmarkStatus.NOT_BOOKMARKED) {
                sql += " AND abt.id IS NULL";
            }
        }

        sql += " ORDER BY att.applied_date DESC";

        Query query = em.createNativeQuery(sql);
        query.setParameter("companyUserId", companyUserId);
        if (jobPostingId != null) query.setParameter("jobPostingId", jobPostingId);

        List<Object[]> resultList = query.getResultList();
        List<ApplicationResponse.ApplicantListDTO> dtoList = new ArrayList<>();

        for (Object[] row : resultList) {
            Integer applicationId = (Integer) row[0];
            Integer resumeId = (Integer) row[1];
            String applicantName = (String) row[2];
            String jobTitle = (String) row[3];
            LocalDate appliedDate = ((java.sql.Date) row[4]).toLocalDate();

            BookmarkStatus resultBookmarkStatus = Boolean.TRUE.equals(row[5])
                    ? BookmarkStatus.BOOKMARKED
                    : BookmarkStatus.NOT_BOOKMARKED;

            ViewStatus resultViewStatus = ViewStatus.valueOf((String) row[7]);

            dtoList.add(new ApplicationResponse.ApplicantListDTO(
                    applicationId,
                    resumeId,
                    applicantName,
                    jobTitle,
                    appliedDate,
                    resultBookmarkStatus,
                    resultViewStatus
            ));
        }
        return dtoList;
    }

}
