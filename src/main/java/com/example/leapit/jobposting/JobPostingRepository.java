package com.example.leapit.jobposting;

import com.example.leapit.jobposting.techstack.JobPostingTechStack;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

    // 기술 스택 여러 개 저장
    public void saveAllJobPostingTechStacks(List<JobPostingTechStack> techStacks) {
        for (JobPostingTechStack techStack : techStacks) {
            em.persist(techStack);
        }
    }
}
