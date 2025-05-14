package com.example.leapit.jobposting;

import com.example.leapit.common.techstack.TechStack;
import com.example.leapit.common.techstack.TechStackRepository;
import com.example.leapit.jobposting.techstack.JobPostingTechStack;
import com.example.leapit.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final TechStackRepository techStackRepository;

    @Transactional
    public JobPostingRequest.SaveDTO save(JobPostingRequest.SaveDTO dto, User user) {
        // 1. 채용공고 저장
        JobPosting jobPosting = dto.toEntity(user);
        jobPostingRepository.save(jobPosting);

        // 2. techStackCodes → TechStack 엔티티 변환 후 연관관계 매핑
        List<JobPostingTechStack> techStacks = new ArrayList<>();
        if (dto.getTechStackCodes() != null) {
            for (String code : dto.getTechStackCodes()) {
                TechStack techStackEntity = techStackRepository.findByCode(code)
                        .orElseThrow(() -> new IllegalArgumentException("해당 기술스택이 존재하지 않습니다: " + code));

                JobPostingTechStack jobPostingTechStack = JobPostingTechStack.builder()
                        .jobPosting(jobPosting)
                        .techStack(techStackEntity)
                        .build();

                techStacks.add(jobPostingTechStack);
            }
        }
        
        // 3. 기술스택 저장
        jobPostingRepository.saveAllJobPostingTechStacks(techStacks);
        return dto;
    }

}
