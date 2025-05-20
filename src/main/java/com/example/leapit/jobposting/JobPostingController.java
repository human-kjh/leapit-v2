package com.example.leapit.jobposting;

import com.example.leapit._core.util.Resp;
import com.example.leapit.user.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class JobPostingController {
    private final JobPostingService jobPostingService;
    private final HttpSession session;

    // 채용공고 등록
    @PostMapping("/s/api/company/jobposting")
    public ResponseEntity<?> save(@RequestBody @Valid JobPostingRequest.SaveDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        JobPostingResponse.DTO respDTO = jobPostingService.save(reqDTO, sessionUser);
        return Resp.ok(respDTO);
    }

    // 채용공고 등록 폼에 뿌릴 데이터 ex) 포지션, 지역, 커리어레벨 등
    @GetMapping("/s/api/company/jobposting/new")
    public ResponseEntity<?> getSaveForm() {
        JobPostingResponse.SaveDTO respDTO = jobPostingService.getSaveForm();
        return Resp.ok(respDTO);
    }

    // 기업 채용공고 상세보기
    @GetMapping("/s/api/company/jobposting/{id}/detail")
    public ResponseEntity<?> companyGetDetailForm(@PathVariable Integer id) {
        JobPostingResponse.DTO respDTO = jobPostingService.getDetailCompany(id);
        return Resp.ok(respDTO);
    }

    // 구직자 채용공고 상세보기
    @GetMapping("/s/api/personal/jobposting/{id}/detail")
    public ResponseEntity<?> personalGetDetailForm(@PathVariable Integer id) {
        JobPostingResponse.DetailPersonalDTO respDTO = jobPostingService.getDetailPersonal(id);
        return Resp.ok(respDTO);
    }

    // 채용공고 삭제
    @DeleteMapping("/s/api/company/jobposting/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        jobPostingService.delete(id, sessionUser.getId());
        return Resp.ok(null);
    }

    // 채용공고 수정 화면
    @GetMapping("/s/api/company/jobposting/{id}/edit")
    public ResponseEntity<?> getUpdateForm(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        JobPostingResponse.UpdateDTO respDTO = jobPostingService.getUpdateForm(id, sessionUser.getId());
        return Resp.ok(respDTO);
    }

    // 채용공고 수정
    @PutMapping("/s/api/company/jobposting/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody JobPostingRequest.UpdateDTO reqDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        JobPostingResponse.DTO respDTO = jobPostingService.update(id, sessionUser.getId(), reqDTO);
        return Resp.ok(respDTO);
    }
}