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

    @GetMapping("/s/company/applicant/list")
    public String applicantList(ApplicationRequest.ApplicantListDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        Boolean isViewed = null;
        if ("열람".equals(isViewedStr)) {
            isViewed = true;
        } else if ("미열람".equals(isViewedStr)) {
            isViewed = false;
        }

        Boolean isBookmark = null;
        if ("true".equals(isBookmarkStr)) isBookmark = true;

        ApplicationResponse.ApplicantListPageDTO respDTO =
                applicationService.findApplicantPageWithFilters(sessionUser.getId(), reqDTO.getJobPostingId(), passStatus, isViewed, isBookmark);

        request.setAttribute("models", respDTO);

        return "company/applicant/list";
    }
}
