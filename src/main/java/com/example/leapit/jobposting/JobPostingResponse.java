package com.example.leapit.jobposting;

import lombok.Data;

public class JobPostingResponse {

    // 진행중과 마감된 리스트 조회
    @Data
    public static class ListDTO {
        private Integer jobPostingId;
        private String title;

        public ListDTO(Integer jobPostingId, String title) {
            this.jobPostingId = jobPostingId;
            this.title = title;
        }
    }
}