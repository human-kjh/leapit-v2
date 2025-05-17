package com.example.leapit.application;

import com.example.leapit.common.enums.BookmarkStatus;
import com.example.leapit.common.enums.ViewStatus;
import lombok.Data;

public class ApplicationRequest {

    @Data
    public static class ApplicantListDTO {
        private Integer jobPostingId;
        private String jobPosition;
        private Boolean isClosed;

        private ViewStatus viewStatus;           // enum으로 변경
        private BookmarkStatus bookmarkStatus;   // enum으로 변경
        private String passStatus;

        public Boolean getIsViewed() {
            return viewStatus != null ? viewStatus.toBoolean() : null;
        }

        public Boolean getIsBookmark() {
            return bookmarkStatus != null ? bookmarkStatus.toBoolean() : null;
        }
    }

}