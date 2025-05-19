package com.example.leapit.jobposting;

import com.example.leapit._core.util.Resp;
import com.example.leapit.user.User;
import com.example.leapit.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import com.example.leapit._core.util.Resp;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JobPostingController {
    private final JobPostingService jobPostingService;
    private final UserRepository userRepository;

    private final HttpSession session;

    @PostMapping("/s/api/company/jobposting")
    public ResponseEntity<?> save(@RequestBody @Valid JobPostingRequest.SaveDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        JobPostingResponse.DTO respDTO = jobPostingService.save(reqDTO, sessionUser);
        return Resp.ok(respDTO);
    }

    @GetMapping("/s/api/company/jobposting/new")
    public ResponseEntity<?> getSaveForm() {
        JobPostingResponse.SaveDTO respDTO = jobPostingService.getSaveForm();
        return Resp.ok(respDTO);
    }

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