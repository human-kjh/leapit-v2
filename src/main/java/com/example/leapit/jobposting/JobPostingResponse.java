package com.example.leapit.jobposting;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public class JobPostingResponse {

    @Data
    public static class SaveDTO {
        private Integer id;
        private String title;
        private String positionType;
        private Integer minCareerLevel;
        private Integer maxCareerLevel;
        private String educationLevel;
        private Integer addressRegionId;
        private Integer addressSubRegionId;
        private String addressDetail;
        private String serviceIntro;
        private LocalDate deadline;
        private String responsibility;
        private String qualification;
        private String preference;
        private String benefit;
        private String additionalInfo;
        private Integer viewCount;
        private String createdAt;

        private List<String> techStacks;

        public SaveDTO(JobPosting jobPosting) {
            this.id = jobPosting.getId();
            this.title = jobPosting.getTitle();
            this.positionType = jobPosting.getPositionType();
            this.minCareerLevel = jobPosting.getMinCareerLevel();
            this.maxCareerLevel = jobPosting.getMaxCareerLevel();
            this.educationLevel = jobPosting.getEducationLevel();
            this.addressRegionId = jobPosting.getAddressRegionId();
            this.addressSubRegionId = jobPosting.getAddressSubRegionId();
            this.addressDetail = jobPosting.getAddressDetail();
            this.serviceIntro = jobPosting.getServiceIntro();
            this.deadline = jobPosting.getDeadline();
            this.responsibility = jobPosting.getResponsibility();
            this.qualification = jobPosting.getQualification();
            this.preference = jobPosting.getPreference();
            this.benefit = jobPosting.getBenefit();
            this.additionalInfo = jobPosting.getAdditionalInfo();
            this.viewCount = jobPosting.getViewCount();
            this.createdAt = jobPosting.getCreatedAt().toString();
            this.techStacks = jobPosting.getJobPostingTechStacks().stream()
                    .map(jpts -> jpts.getTechStack())
                    .toList();
        }
    }

    @Data
    public static class DTO {
        private Integer id;
        private String title;
        private String positionType;
        private Integer minCareerLevel;
        private Integer maxCareerLevel;
        private String educationLevel;
        private Integer addressRegionId;
        private Integer addressSubRegionId;
        private String addressDetail;
        private String serviceIntro;
        private LocalDate deadline;
        private String responsibility;
        private String qualification;
        private String preference;
        private String benefit;
        private String additionalInfo;
        private Integer viewCount;
        private String createdAt;

        private List<String> techStacks;

        public DTO(JobPosting jobPosting) {
            this.id = jobPosting.getId();
            this.title = jobPosting.getTitle();
            this.positionType = jobPosting.getPositionType();
            this.minCareerLevel = jobPosting.getMinCareerLevel();
            this.maxCareerLevel = jobPosting.getMaxCareerLevel();
            this.educationLevel = jobPosting.getEducationLevel();
            this.addressRegionId = jobPosting.getAddressRegionId();
            this.addressSubRegionId = jobPosting.getAddressSubRegionId();
            this.addressDetail = jobPosting.getAddressDetail();
            this.serviceIntro = jobPosting.getServiceIntro();
            this.deadline = jobPosting.getDeadline();
            this.responsibility = jobPosting.getResponsibility();
            this.qualification = jobPosting.getQualification();
            this.preference = jobPosting.getPreference();
            this.benefit = jobPosting.getBenefit();
            this.additionalInfo = jobPosting.getAdditionalInfo();
            this.viewCount = jobPosting.getViewCount();
            this.createdAt = jobPosting.getCreatedAt().toString();
            this.techStacks = jobPosting.getJobPostingTechStacks().stream()
                    .map(jpts -> jpts.getTechStack())
                    .toList();
        }
    }
}
