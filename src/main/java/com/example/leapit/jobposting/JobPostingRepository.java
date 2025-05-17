package com.example.leapit.jobposting;

import com.example.leapit.application.ApplicationResponse;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class JobPostingRepository {
    private final EntityManager em;

    public List<ApplicationResponse.IsClosedDTO> findAllWithIsClosedByCompanyUserId(Integer companyUserId) {
        String jpql = """
                    SELECT new com.example.leapit.application.ApplicationResponse$IsClosedDTO(
                        jpt.id,
                        jpt.title,
                        CASE WHEN jpt.deadline < CURRENT_DATE THEN true ELSE false END
                    )
                    FROM JobPosting jpt
                    JOIN jpt.user u
                    WHERE u.id = :companyUserId
                    ORDER BY jpt.createdAt DESC
                """;

        return em.createQuery(jpql, ApplicationResponse.IsClosedDTO.class)
                .setParameter("companyUserId", companyUserId)
                .getResultList();
    }
}

