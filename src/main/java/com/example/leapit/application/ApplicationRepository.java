package com.example.leapit.application;

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

    public List<ApplicationResponse.CompanyeApplicantDto> findAllApplicantsByFilter(
            Integer companyUserId, Integer jobPostingId, String passStatus, Boolean isViewed, Boolean isBookmark) {

        String sql = """
                    SELECT
                        att.id AS applicationId,
                        rt.id AS resumeId,
                        ut.name AS applicantName,
                        jpt.title AS jobTitle,
                        att.applied_date AS appliedDate,
                        CASE WHEN abt.id IS NOT NULL THEN TRUE ELSE FALSE END AS isBookmarked,
                        CASE
                            WHEN att.is_viewed = false THEN '미열람'
                            WHEN att.is_viewed = true AND att.is_passed IS NULL THEN '열람'
                            WHEN att.is_passed = true THEN '합격'
                            WHEN att.is_passed = false THEN '불합격'
                        END AS evaluationStatus,
                        att.is_viewed AS isViewed
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

        if ("합격".equals(passStatus)) {
            sql += " AND att.is_passed = true";
        } else if ("불합격".equals(passStatus)) {
            sql += " AND att.is_passed = false";
        }

        if (isViewed != null) {
            sql += " AND att.is_viewed = " + (isViewed ? "true" : "false");
        }

        if (Boolean.TRUE.equals(isBookmark)) {
            sql += " AND abt.id IS NOT NULL";
        }

        sql += " ORDER BY att.applied_date DESC";

        Query query = em.createNativeQuery(sql);
        query.setParameter("companyUserId", companyUserId);
        if (jobPostingId != null) query.setParameter("jobPostingId", jobPostingId);

        List<Object[]> objects = query.getResultList();
        List<ApplicationResponse.CompanyeApplicantDto> object = new ArrayList<>();

        for (Object[] obs : objects) {
            Integer applicationId = ((int) obs[0]);
            Integer resumeId = ((int) obs[1]);
            String applicantName = (String) obs[2];
            String jobTitle = (String) obs[3];
            LocalDate appliedDate = ((java.sql.Date) obs[4]).toLocalDate();
            Boolean isBookmarked = (Boolean) obs[5];
            String evaluationStatus = (String) obs[6];
            Boolean isViewedResult = (Boolean) obs[7];

            ApplicationResponse.CompanyeApplicantDto dto = new ApplicationResponse.CompanyeApplicantDto(
                    applicationId, resumeId, applicantName, jobTitle, appliedDate,
                    isBookmarked, evaluationStatus, isViewedResult
            );
            object.add(dto);
        }
        return object;
    }
}
