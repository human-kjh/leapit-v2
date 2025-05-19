package com.example.leapit.application;

import com.example.leapit._core.error.ex.ExceptionApi404;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    // 개인 마이페이지 지원 현황 관리
    public ApplicationResponse.MyPageDTO getMyApplication(Integer userId) {
        if (userId == null) throw new ExceptionApi404("회원정보가 존재하지 않습니다.");

        // 지원 현황 통계
        ApplicationResponse.StatusDTO statusDTO = applicationRepository.findSummaryByUserId(userId);
        if (statusDTO == null) {
            statusDTO = new ApplicationResponse.StatusDTO(0L, 0L, 0L);
        }

        // 지원 현황 목록 조회
        List<ApplicationResponse.ItemDTO> applicationDTOs = applicationRepository.findApplicationsByUserId(userId);
        // respDTO에 담기
        ApplicationResponse.MyPageDTO respDTO = new ApplicationResponse.MyPageDTO(statusDTO, applicationDTOs);
        return respDTO;
    }
}