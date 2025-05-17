package com.example.leapit.application;

import com.example.leapit.jobposting.JobPostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final JobPostingRepository jobPostingRepository;

    // 기업 지원자현황 관리
    public ApplicationResponse.ApplicantListPageDTO applicantList(Integer companyUserId, ApplicationRequest.ApplicantListDTO reqDTO) {
        // 1. 진행중과 마감된 리스트 조회
        List<ApplicationResponse.IsClosedDTO> positions = jobPostingRepository.findAllWithIsClosedByCompanyUserId(companyUserId);

        // 2. 지원받은 이력서 목록 조회
        List<ApplicationResponse.CompanyeApplicantDto> applicants = applicationRepository.findAllApplicantsByFilter(companyUserId, reqDTO.getJobPostingId(), reqDTO.getPassStatus(), reqDTO.getIsViewed(), reqDTO.getIsBookmark());

        // 3. pageDTO에 담아서 컨트롤러에 넘김
        ApplicationResponse.ApplicantListPageDTO respDTO = new ApplicationResponse.ApplicantListPageDTO(positions, applicants);

        return respDTO;
    }
}