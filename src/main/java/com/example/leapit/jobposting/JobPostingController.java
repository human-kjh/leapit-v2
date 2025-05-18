package com.example.leapit.jobposting;

import com.example.leapit._core.util.Resp;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JobPostingController {
    private final JobPostingService jobPostingService;
    private final HttpSession session;

    // 구직자 - 채용공고 목록
    @GetMapping("/s/personal/jobposting/list")
    public ResponseEntity<?> getList(JobPostingRequest.JobPostingListRequestDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        Integer sessionUserId = (sessionUser != null) ? sessionUser.getId() : null;

        JobPostingResponse.JobPostingListFilterDTO respDTO =
                jobPostingService.getList(
                        reqDTO.getRegionIdAsInteger(),
                        reqDTO.getSubRegionIdAsInteger(),
                        reqDTO.getCareerLevelOrNull(),
                        reqDTO.getTechStackCodeOrNull(),
                        reqDTO.getSelectedPositionOrNull(),
                        reqDTO.getSortTypeOrDefault(),
                        sessionUserId
                );
        return Resp.ok(respDTO);
    }
}