package com.example.leapit.application.bookmark;

import com.example.leapit._core.error.ex.ExceptionApi401;
import com.example.leapit._core.util.Resp;
import com.example.leapit.user.User;
import com.example.leapit.user.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ApplicationBookmarkController {
    private final ApplicationBookmarkService bookmarkService;
    private final HttpSession session;

    // 기업 스크랩 등록 application_bookmark
    @PostMapping("/s/api/company/bookmark")
    public Resp<?> saveApplicationBookmark(@Valid @RequestBody ApplicationBookmarkRequest.SaveDTO reqDTO, Errors errors) {

        try {
            ApplicationBookmarkResponse.SaveDTO respDTO = bookmarkService.saveApplicantBookmarkByUserId(reqDTO, sessionUser.getId());
            return Resp.ok(respDTO);
        } catch (RuntimeException e) {
            return Resp.fail(400, e.getMessage());
        }
    }

    // 기업 스크랩 삭제 application_bookmark
    @DeleteMapping("/s/api/company/bookmark/{id}")
    public Resp<?> deleteApplicationBookmark(@PathVariable("id") Integer bookmarkId) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) throw new ExceptionApi401("로그인 후 이용");

        bookmarkService.deleteApplicationBookmarkByApplicationId(bookmarkId, sessionUser.getId());
        return Resp.ok("북마크 삭제");
    }
}
