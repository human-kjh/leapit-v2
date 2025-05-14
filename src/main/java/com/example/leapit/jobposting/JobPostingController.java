package com.example.leapit.jobposting;

import com.example.leapit._core.util.Resp;
import com.example.leapit.user.User;
import com.example.leapit.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JobPostingController {
    private final JobPostingService jobPostingService;
    private final UserRepository userRepository;

    private final HttpSession session;

    @PostMapping("/company/jobposting/save")
    public ResponseEntity<?> save(@Valid @RequestBody JobPostingRequest.SaveDTO saveDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        JobPostingRequest.SaveDTO reqDTO = jobPostingService.save(saveDTO, sessionUser);
        return Resp.ok(reqDTO);
    }
}