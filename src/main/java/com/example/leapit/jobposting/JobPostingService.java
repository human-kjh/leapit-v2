package com.example.leapit.jobposting;

import com.example.leapit.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;

    // 채용공고 저장
    @Transactional
    public JobPostingResponse.DTO save(JobPostingRequest.SaveDTO reqDTO, User sessionUser) {
        // 1. 연관된 기술스택까지 포함된 JobPosting 생성
        JobPosting jobPosting = reqDTO.toEntity(sessionUser);

        // 2. 저장
        JobPosting jobPostingPS = jobPostingRepository.save(jobPosting);

        // 3. 응답용 DTO로 반환
        return new JobPostingResponse.DTO(jobPostingPS);
    }

    // 채용공고 저장 화면에 필요한 데이터 불러오기
    @Transactional
    public JobPostingResponse.SaveDTO getSaveForm(Integer userId) {
        JobPosting jobPosting = jobPostingRepository.findById(userId);
        return new JobPostingResponse.SaveDTO(jobPosting);
    }
}
