package com.example.leapit.jobposting;

import com.example.leapit.common.enums.CareerLevel;
import com.example.leapit.common.region.SubRegion;
import com.example.leapit.companyinfo.CompanyInfo;
import com.example.leapit.companyinfo.CompanyInfoResponse;
import com.example.leapit.jobposting.techstack.JobPostingTechStack;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JobPostingResponse {

    @Data
    public static class SaveDTO {
        private List<String> positionTypes;
        private List<String> techStackCodes;
        private List<RegionDTO> regions;
        private List<CareerLevel> careerLevels;

        public SaveDTO(List<String> positionTypes, List<String> techStackCodes, List<RegionDTO> regions, List<CareerLevel> careerLevels) {
            this.positionTypes = positionTypes;
            this.techStackCodes = techStackCodes;
            this.regions = regions;
            this.careerLevels = careerLevels;
        }
    }

    @Data
    public static class RegionDTO {
        private Integer id;
        private String name;
        private List<SubRegionDTO> subRegions;

        public RegionDTO(Integer id, String name, List<SubRegion> subRegions) {
            this.id = id;
            this.name = name;
            this.subRegions = subRegions.stream()
                    .map(sr -> new SubRegionDTO(sr.getId(), sr.getName()))
                    .toList();
        }
    }

    @Data
    public static class SubRegionDTO {
        private Integer id;
        private String name;

        public SubRegionDTO(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public static class CareerLevelDTO {
        private CareerLevel careerLevel;

        public CareerLevelDTO(CareerLevel careerLevel) {
            this.careerLevel = careerLevel;
        }
    }

    @Data
    public static class DTO {
        private Integer id;
        private String title;
        private String positionType;
        private String minCareerLevel;
        private String maxCareerLevel;
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
            this.minCareerLevel = jobPosting.getMinCareerLevel().getLabel();
            this.maxCareerLevel = jobPosting.getMaxCareerLevel().getLabel();
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

    // 구직자 - 메인페이지 채용공고 목록
    @Data
    public static class MainDTO {

        private List<MainRecentJobPostingDTO> recentJobPostings; // 최신공고 3개
        private List<MainPopularJobPostingDTO> popularJobPostings; // 인기공고 8개

        @Data
        public static class MainRecentJobPostingDTO {
            private Integer id;
            private String title;
            private String companyName;
            private String image;
            private boolean isActive;
            private boolean isBookmarked;

            public MainRecentJobPostingDTO(JobPosting jp, CompanyInfo companyInfo, String imageString, boolean isActive, boolean isBookmarked) {
                this.id = jp.getId();
                this.title = jp.getTitle();
                this.companyName = companyInfo.getCompanyName();
                this.image = imageString;
                this.isActive = isActive;
                this.isBookmarked = isBookmarked;
            }
        }

        @Data
        public static class MainPopularJobPostingDTO {
            private Integer id;
            private String title;
            private String companyName;
            private String image;
            private String address;
            private String career;
            private int dDay;
            private int viewCount;
            private boolean isBookmarked;
            private List<CompanyInfoResponse.DetailDTO.JobPostingDTO.TechStackDTO> techStacks;

            public MainPopularJobPostingDTO(JobPosting jp,
                                            String companyName,
                                            String imageString,
                                            String address,
                                            List<JobPostingTechStack> techStacks,
                                            boolean isBookmarked) {
                this.id = jp.getId();
                this.title = jp.getTitle();
                this.companyName = companyName;
                this.image = imageString;
                this.address = address;
                this.career = formatCareer(jp.getMinCareerLevel().getLabel(), jp.getMaxCareerLevel().getLabel());
                this.dDay = calculateDDay(jp.getDeadline());
                this.viewCount = jp.getViewCount();
                this.isBookmarked = isBookmarked;
                this.techStacks = new ArrayList<>();
                for (JobPostingTechStack stack : techStacks) {
                    String code = stack.getTechStack();
                    this.techStacks.add(new CompanyInfoResponse.DetailDTO.JobPostingDTO.TechStackDTO(code));
                }
            }

            // 문자열 기반 포맷 함수 (YEAR_3, OVER_10 등)
            private String formatCareer(String minStr, String maxStr) {
                try {
                    int min = toYearValue(minStr);
                    int max = toYearValue(maxStr);
                    return formatCareer(min, max);
                } catch (Exception e) {
                    return "경력 정보 오류";
                }
            }

            // 기존 포맷 함수 유지
            private String formatCareer(int min, int max) {
                if ((min == 0 && max == 0)) return "경력무관";
                if (min > max) return "경력 정보 오류";
                return min + "-" + max + "년";
            }

            // 문자열 enum 이름 → 숫자로 변환
            private int toYearValue(String enumName) {
                if (enumName == null) return 0;
                if (enumName.startsWith("YEAR_")) {
                    return Integer.parseInt(enumName.substring(5)); // YEAR_5 → 5
                } else if (enumName.equals("OVER_10")) {
                    return 10;
                }
                return 0;
            }

            private int calculateDDay(LocalDate deadline) {
                return (int) java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), deadline);
            }
        }


        public MainDTO(List<MainRecentJobPostingDTO> recent, List<MainPopularJobPostingDTO> popular) {
            this.recentJobPostings = recent;
            this.popularJobPostings = popular;
        }
    }
}
