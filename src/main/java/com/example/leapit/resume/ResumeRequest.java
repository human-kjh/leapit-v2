package com.example.leapit.resume;

import com.example.leapit.user.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class ResumeRequest {
    @Data
    public static class SaveDTO {
        @NotEmpty(message = "제목을 입력해주세요.")
        private String title;
        private String photoUrl;
        private String summary;
        @NotEmpty(message = "직무를 선택해주세요.")
        private String positionType;
        @NotEmpty(message = "기술스택을 선택해주세요.")
        private List<String> resumeTechStacks;
        @Valid
        private List<EducationDTO> educations;
        @Valid
        private List<ProjectDTO> projects;
        @Valid
        private List<ExperienceDTO> experiences;
        @Valid
        private List<LinkDTO> links;
        @Valid
        private List<TrainingDTO> trainings;
        @Valid
        private List<EtcDTO> etcs;

        private String selfIntroduction;

        @Data
        public static class EducationDTO {
            @NotNull(message = "졸업일을 선택해주세요.")
            private LocalDate graduationDate;
            private Boolean isDropout;
            @NotEmpty(message ="학력구분을 선택해주세요.")
            private String educationLevel;
            private String schoolName;
            private String major;
            private BigDecimal gpa;
            private BigDecimal gpaScale;
        }

        @Data
        public static class ProjectDTO {
            @NotNull(message = "시작일을 선택해주세요.")
            private LocalDate startDate;
            private LocalDate endDate;
            private Boolean isOngoing;

            @NotEmpty(message = "제목을 입력해주세요.")
            private String title;
            private String summary;

            @NotEmpty(message = "설명을 입력해주세요.")
            private String description;
            private String repositoryUrl;

            @NotEmpty(message = "기술 스택을 1개 이상 선택해주세요.")
            @Valid
            private List<String> techStacks;
        }

        @Data
        public static class ExperienceDTO {
            @NotNull(message = "시작일을 선택해주세요.")
            private LocalDate startDate;
            private LocalDate endDate;
            private Boolean isEmployed;
            private String companyName;
            private String summary;
            private String position;
            private String responsibility;
            @NotEmpty(message = "기술 스택을 1개 이상 선택해주세요.")
            @Valid
            private List<String> techStacks;
        }

        @Data
        public static class LinkDTO {
            private String title;
            private String url;
        }

        @Data
        public static class TrainingDTO {
            private LocalDate startDate;
            private LocalDate endDate;
            private Boolean isOngoing;
            private String courseName;
            private String institutionName;
            private String description;
            private Timestamp createdAt;
            private List<String> techStacks;
        }

        @Data
        public static class EtcDTO {
            private LocalDate startDate;
            private LocalDate endDate;
            private Boolean hasEndDate;
            private String title;
            private String etcType;
            private String institutionName;
            private String description;
        }

        public Resume toEntity(User user){
            Resume resume = Resume.builder()
                    .photoUrl(photoUrl)
                    .summary(summary)
                    .positionType(positionType)
                    .selfIntroduction(selfIntroduction)
                    .user(user)
                    .build();

            if(resumeTechStacks != null){

            }


            return resume;
        }
    }
}
