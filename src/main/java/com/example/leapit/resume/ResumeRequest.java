package com.example.leapit.resume;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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

        public static class EducationDTO {
            @NotNull()
            private LocalDate graduationDate;
            private Boolean isDropout;
            private String educationLevel;
            private String schoolName;
            private String major;
            private BigDecimal gpa;
            private BigDecimal gpaScale;
        }
    }
}
