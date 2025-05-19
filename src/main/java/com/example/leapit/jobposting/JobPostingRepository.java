package com.example.leapit.jobposting;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class JobPostingRepository {
    private final EntityManager em;

    // 채용공고 저장
    public JobPosting save(JobPosting jobPosting) {
        em.persist(jobPosting);
        return jobPosting;
    }

    // 아이디로 채용공고 찾기
    public JobPosting findById(Integer id) {
        return em.find(JobPosting.class, id);
    }

}
