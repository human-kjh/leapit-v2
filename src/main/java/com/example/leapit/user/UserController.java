package com.example.leapit.user;

import com.example.leapit._core.error.ex.ExceptionApi400;
import com.example.leapit._core.util.JwtUtil;
import com.example.leapit._core.util.Resp;
import com.example.leapit.jobposting.JobPostingResponse;
import com.example.leapit.jobposting.JobPostingService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final JobPostingService jobPostingService;
    private final HttpSession session;

    @PostMapping("/personal/join")
    public ResponseEntity<?> personalJoin(@Valid @RequestBody UserRequest.PersonalJoinDTO reqDTO, Errors errors) {
        UserResponse.DTO respDTO = userService.join(reqDTO);
        return Resp.ok(respDTO);
    }

    @PostMapping("/company/join")
    public ResponseEntity<?> companyJoin(@Valid @RequestBody UserRequest.CompanyJoinDTO reqDTO) {
        UserResponse.DTO respDTO = userService.join(reqDTO);
        return Resp.ok(respDTO);
    }

    @GetMapping("/api/check-username-available/{username}")
    public ResponseEntity<?> checkUsernameAvailable(@PathVariable("username") String username) {
        Map<String, Object> respDTO = userService.checkUsernameAvailable(username);
        return Resp.ok(respDTO);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequest.LoginDTO loginDTO, HttpServletResponse response, Errors errors) {
        UserResponse.TokenDTO respDTO = userService.login(loginDTO);
        return Resp.ok(respDTO);
    }

    @PutMapping("/s/company/user")
    public ResponseEntity<?> companyUpdate(@Valid @RequestBody UserRequest.CompanyUpdateDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (!reqDTO.getNewPassword().equals(reqDTO.getConfirmPassword())) throw new ExceptionApi400("입력한 비밀번호가 다릅니다.");
        UserResponse.UpdateDTO respDTO = userService.update(reqDTO, sessionUser.getId());
        return Resp.ok(respDTO);
    }

    @PutMapping("/s/personal/user")
    public ResponseEntity<?> personalUpdate(@Valid @RequestBody UserRequest.PersonalUpdateDTO reqDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (!reqDTO.getNewPassword().equals(reqDTO.getConfirmPassword())) throw new ExceptionApi400("입력한 비밀번호가 다릅니다.");
        UserResponse.UpdateDTO respDTO = userService.update(reqDTO, sessionUser.getId());
        return Resp.ok(respDTO);
    }

    @GetMapping("/")
    public ResponseEntity<?> index(
            @RequestHeader(value = "Authorization", required = false) String accessToken) {

        Integer userId = null;

        // 1. 세션 기반 인증
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser != null) {
            userId = sessionUser.getId();
        }

        // 2. 토큰 기반 인증 (세션이 없을 때만)
        else if (accessToken != null && accessToken.startsWith("Bearer ")) {
            try {
                String token = accessToken.replace("Bearer ", "");
                userId = JwtUtil.getUserId(token);
            } catch (Exception e) {
                System.out.println("JWT 파싱 실패: " + e.getMessage());
                // userId는 null로 둬도 됨 → 북마크 없이 동작
            }
        }

        List<JobPostingResponse.MainDTO.MainRecentJobPostingDTO> recent = jobPostingService.getRecentPostings(userId);
        List<JobPostingResponse.MainDTO.MainPopularJobPostingDTO> popular = jobPostingService.getPopularJobPostings(userId);

        JobPostingResponse.MainDTO respDTO = new JobPostingResponse.MainDTO(recent, popular);

        return Resp.ok(respDTO);

    }
}