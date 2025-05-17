package com.example.leapit.application;

import lombok.Data;

public class ApplicationRequest {

    @Data
    public static class ApplicantListDTO {
        private Integer jobPostingId;
        private String jobPosition;
        private Boolean isClosed;
        private String isViewedStr;
        private String isBookmarkStr;
        private String passStatus;

        public Boolean getIsViewed() {
            if ("열람".equals(isViewedStr)) return true;
            if ("미열람".equals(isViewedStr)) return false;
            return null;
        }

        public Boolean getIsBookmark() {
            if ("true".equals(isBookmarkStr)) return true;
            if ("false".equals(isBookmarkStr)) return false;
            return null;
        }
    }

}