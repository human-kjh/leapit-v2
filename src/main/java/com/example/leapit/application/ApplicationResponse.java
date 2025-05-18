package com.example.leapit.application;

import com.example.leapit.common.enums.BookmarkStatus;
import com.example.leapit.common.enums.ViewStatus;
import com.example.leapit.jobposting.JobPostingResponse;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public class ApplicationResponse {

    // 기업 지원자현황 관리 페이지 RespDTO
    @Data
    public static class ApplicantListPageDTO {
        private List<ApplicantListDTO> applicants; // 지원받은 이력서 목록 조회
        private List<JobPostingResponse.ListDTO> allPositions; // 전체 공고 조회
        private List<JobPostingResponse.ListDTO> openPositions; // 진행중인 공고 조회
        private List<JobPostingResponse.ListDTO> closedPostions; // 마감된 공고 조회

        public ApplicantListPageDTO(List<ApplicantListDTO> applicants, List<JobPostingResponse.ListDTO> allPositions, List<JobPostingResponse.ListDTO> openPositions, List<JobPostingResponse.ListDTO> closedPostions) {
            this.applicants = applicants;
            this.allPositions = allPositions;
            this.openPositions = openPositions;
            this.closedPostions = closedPostions;
        }
    }


    // 지원자 목록 조회
    @Data
    public static class ApplicantListDTO {
        private List<JobPostingResponse.ListDTO> positions;
        private Integer applicationId;
        private Integer resumeId;
        private String applicantName;
        private String jobTitle;
        private LocalDate appliedDate;
        private BookmarkStatus bookmarkStatus;
        private ViewStatus viewStatus;

        public ApplicantListDTO(Integer applicationId,
                                Integer resumeId,
                                String applicantName,
                                String jobTitle,
                                LocalDate appliedDate,
                                BookmarkStatus bookmarkStatus,
                                ViewStatus viewStatus) {
            this.applicationId = applicationId;
            this.resumeId = resumeId;
            this.applicantName = applicantName;
            this.jobTitle = jobTitle;
            this.appliedDate = appliedDate;
            this.bookmarkStatus = bookmarkStatus;
            this.viewStatus = viewStatus;
        }
    }
}

