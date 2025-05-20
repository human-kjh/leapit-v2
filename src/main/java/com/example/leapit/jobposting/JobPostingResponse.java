package com.example.leapit.jobposting;

import com.example.leapit.common.enums.CareerLevel;
import com.example.leapit.common.enums.EducationLevel;
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
        private List<EducationLevel> educationLevels;

        public SaveDTO(List<String> positionTypes, List<String> techStackCodes, List<RegionDTO> regions, List<CareerLevel> careerLevels, List<EducationLevel> educationLevels) {
            this.positionTypes = positionTypes;
            this.techStackCodes = techStackCodes;
            this.regions = regions;
            this.careerLevels = careerLevels;
            this.educationLevels = educationLevels;
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

    @Data
    public static class DetailPersonalDTO {
        private DTO companyDTO;
        private CompanyInfoDTO companyInfo;

        public DetailPersonalDTO(DTO companyDTO, CompanyInfoDTO companyInfo) {
            this.companyDTO = companyDTO;
            this.companyInfo = companyInfo;
        }

        @Data
        public static class CompanyInfoDTO {
            private Integer id;
            private String logoImage;
            private String companyName;
            private LocalDate establishmentDate;
            private String mainService;

            public CompanyInfoDTO(CompanyInfo companyInfo) {
                this.id = companyInfo.getId();
                this.logoImage = companyInfo.getLogoImage();
                this.companyName = companyInfo.getCompanyName();
                this.establishmentDate = companyInfo.getEstablishmentDate();
                this.mainService = companyInfo.getMainService();
            }
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
            this.minCareerLevel = jobPosting.getMinCareerLevel().label;
            this.maxCareerLevel = jobPosting.getMaxCareerLevel().label;
            this.educationLevel = jobPosting.getEducationLevel().label;
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
                this.career = formatCareer(jp.getMinCareerLevel().name(), jp.getMaxCareerLevel().name());
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
                if (min == 0 && max == 0) return "경력무관";
                if (min == 0 && max > 0) return "신입, 경력 1-" + max + "년";
                if (min > max) return "경력 정보 오류";
                return "경력 " + min + "-" + max + "년";
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




