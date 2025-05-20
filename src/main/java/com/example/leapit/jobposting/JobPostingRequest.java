package com.example.leapit.jobposting;

import com.example.leapit.common.enums.CareerLevel;
import com.example.leapit.common.enums.EducationLevel;
import com.example.leapit.jobposting.techstack.JobPostingTechStack;
import com.example.leapit.user.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import com.example.leapit.common.enums.CareerLevel;
import com.example.leapit.common.enums.SortType;
import lombok.Data;

public class JobPostingRequest {

    @Data
    public static class SaveDTO {
        @NotEmpty(message = "제목은 필수입니다.")
        private String title;

        @NotEmpty(message = "직무는 필수입니다.")
        private String positionType;
        private CareerLevel minCareerLevel;
        private CareerLevel maxCareerLevel;
        private EducationLevel educationLevel;
        private Integer addressRegionId;
        private Integer addressSubRegionId;
        private String addressDetail;
        private String serviceIntro;

        @NotNull(message = "마감일은 필수입니다.")
        private LocalDate deadline;

        @NotEmpty(message = "담당 업무는 필수입니다.")
        private String responsibility;

        @NotEmpty(message = "자격요건은 필수입니다.")
        private String qualification;

        private String preference;
        private String benefit;
        private String additionalInfo;

        @NotEmpty(message = "기술스택은 필수입니다.")
        private List<String> techStackCodes;

        public JobPosting toEntity(User user) {
            JobPosting jobPosting = JobPosting.builder()
                    .user(user)
                    .title(title)
                    .positionType(positionType)
                    .minCareerLevel(minCareerLevel)
                    .maxCareerLevel(maxCareerLevel)
                    .educationLevel(educationLevel)
                    .addressRegionId(addressRegionId)
                    .addressSubRegionId(addressSubRegionId)
                    .addressDetail(addressDetail)
                    .serviceIntro(serviceIntro)
                    .deadline(deadline)
                    .responsibility(responsibility)
                    .qualification(qualification)
                    .preference(preference)
                    .benefit(benefit)
                    .additionalInfo(additionalInfo)
                    .build();

            for (String techStackCode : techStackCodes) {
                JobPostingTechStack jpts = JobPostingTechStack.builder()
                        .techStack(techStackCode)
                        .jobPosting(jobPosting)
                        .build();
                jobPosting.getJobPostingTechStacks().add(jpts);
            }
            return jobPosting;
        }
    }

    @Data
    public class JobPostingListRequestDTO {

        private String regionId;
        private String subRegionId;
        private String careerLabel;
        private String techStackCode;
        private String positionLabel;
        private SortType sortType; // POPULAR("인기순"), LATEST("최신순")

        // 지역
        public Integer getRegionIdAsInteger() {
            return intOrNull(regionId);
        }

        // 서브지역
        public Integer getSubRegionIdAsInteger() {
            return intOrNull(subRegionId);
        }

        // 경력 enum
        public CareerLevel getCareerLevelOrNull() {
            if (careerLabel != null) {
                for (CareerLevel level : CareerLevel.values()) {
                    if (careerLabel.trim().equals(level.label)) {
                        return level;
                    }
                }
            }
            return null;
        }

        // 기술 스택
        public String getTechStackCodeOrNull() {
            return isNotBlank(techStackCode) ? techStackCode.trim() : null;
        }

        // 직무
        public String getSelectedPositionOrNull() {
            return isNotBlank(positionLabel) ? positionLabel.trim() : null;
        }

        // 정렬 기준 (기본값: 최신순)
        public SortType getSortTypeOrDefault() {
            return sortType != null ? sortType : SortType.LATEST;
        }

        private Integer intOrNull(String value) {
            try {
                return (value != null && !value.isBlank()) ? Integer.parseInt(value.trim()) : null;
            } catch (NumberFormatException e) {
                return null;
            }
        }

        private boolean isNotBlank(String value) {
            return value != null && !value.trim().isEmpty();
        }
    }
}
