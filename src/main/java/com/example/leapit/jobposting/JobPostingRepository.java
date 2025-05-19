package com.example.leapit.jobposting;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class JobPostingRepository {
    private final EntityManager em;

    // 채용공고 저장
    public JobPosting save(JobPosting jobPosting) {
        em.persist(jobPosting);
        return jobPosting;
    }

    // 전체 공고 조회
    public List<JobPostingResponse.ListDTO> findAllByCompanyUserId(Integer companyUserId) {
        String jpql = """
                    SELECT new com.example.leapit.jobposting.JobPostingResponse$ListDTO(
                        jpt.id,
                        jpt.title
                    )
                    FROM JobPosting jpt
                    JOIN jpt.user u
                    WHERE u.id = :companyUserId
                    ORDER BY jpt.createdAt DESC
                """;

        return em.createQuery(jpql, JobPostingResponse.ListDTO.class)
                .setParameter("companyUserId", companyUserId)
                .getResultList();
    }

    // 진행중인 공고 조회
    public List<JobPostingResponse.ListDTO> findOpenByCompanyUserId(Integer companyUserId) {
        String jpql = """
                    SELECT new com.example.leapit.jobposting.JobPostingResponse$ListDTO(
                        jpt.id,
                        jpt.title
                    )
                    FROM JobPosting jpt
                    JOIN jpt.user u
                    WHERE u.id = :companyUserId
                      AND jpt.deadline >= CURRENT_DATE
                    ORDER BY jpt.createdAt DESC
                """;

        return em.createQuery(jpql, JobPostingResponse.ListDTO.class)
                .setParameter("companyUserId", companyUserId)
                .getResultList();
    }

    // 마감된 공고 조회
    public List<JobPostingResponse.ListDTO> findClosedByCompanyUserId(Integer companyUserId) {
        String jpql = """
                    SELECT new com.example.leapit.jobposting.JobPostingResponse$ListDTO(
                        jpt.id,
                        jpt.title
                    )
                    FROM JobPosting jpt
                    JOIN jpt.user u
                    WHERE u.id = :companyUserId
                      AND jpt.deadline < CURRENT_DATE
                    ORDER BY jpt.createdAt DESC
                """;

        return em.createQuery(jpql, JobPostingResponse.ListDTO.class)
                .setParameter("companyUserId", companyUserId)
                .getResultList();
    }
}

