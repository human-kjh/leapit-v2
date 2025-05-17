package com.example.leapit.application;

import com.example.leapit.common.enums.BookmarkStatus;
import com.example.leapit.common.enums.ViewStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public class ApplicationResponse {

    // 기업 지원자현황 관리 페이지 RespDTO
    @Data
    public static class ApplicantListPageDTO {
        private List<IsClosedDTO> positions; // 지원받은 이력서 목록 조회
        private List<CompanyeApplicantDto> applicants; // 진행중과 마감된 리스트 조회

        public ApplicantListPageDTO(List<IsClosedDTO> positions, List<CompanyeApplicantDto> applicants) {
            this.applicants = applicants;
            this.positions = positions;
        }
    }

    // 진행중과 마감된 리스트 조회
    @Data
    public static class IsClosedDTO {
        private Integer jobPostingId;
        private String title;
        private Boolean isClosed;

        public IsClosedDTO(Integer jobPostingId, String title, Boolean isClosed) {
            this.jobPostingId = jobPostingId;
            this.title = title;
            this.isClosed = isClosed;
        }
    }

    // 지원받은 이력서 목록 조회
    @Data
    public static class CompanyeApplicantDto {
        private List<IsClosedDTO> positions;
        private Integer applicationId;
        private Integer resumeId;
        private String applicantName;
        private String jobTitle;
        private LocalDate appliedDate;
        private BookmarkStatus bookmarkStatus;  // enum으로 변경
        private ViewStatus viewStatus;          // enum으로 변경
        private Boolean isViewed;

        public CompanyeApplicantDto(Integer applicationId,
                                    Integer resumeId,
                                    String applicantName,
                                    String jobTitle,
                                    LocalDate appliedDate,
                                    BookmarkStatus bookmarkStatus,
                                    ViewStatus viewStatus,
                                    Boolean isViewed) {
            this.applicationId = applicationId;
            this.resumeId = resumeId;
            this.applicantName = applicantName;
            this.jobTitle = jobTitle;
            this.appliedDate = appliedDate;
            this.bookmarkStatus = bookmarkStatus;
            this.viewStatus = viewStatus;
            this.isViewed = isViewed;
        }
    }
}

