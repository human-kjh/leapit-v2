package com.example.leapit.application;

import com.example.leapit.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ApplicationController {
    private final ApplicationService applicationService;
    private final HttpSession session;

    // 기업 지원자현황 관리
    @GetMapping("/s/company/applicant/list")
    public String applicantList(ApplicationRequest.ApplicantListDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        ApplicationResponse.ApplicantListPageDTO respDTO =
                applicationService.applicantList(sessionUser.getId(), reqDTO);

        return "company/applicant/list";
    }
}
