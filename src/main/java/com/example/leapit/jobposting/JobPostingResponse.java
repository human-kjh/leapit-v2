package com.example.leapit.jobposting;

import com.example.leapit.common.enums.BookmarkStatus;
import com.example.leapit.common.enums.CareerLevel;
import com.example.leapit.common.enums.EducationLevel;
import com.example.leapit.common.region.RegionResponse;
import com.example.leapit.common.region.SubRegion;
import com.example.leapit.companyinfo.CompanyInfo;
import com.example.leapit.companyinfo.CompanyInfoResponse;
import com.example.leapit.jobposting.techstack.JobPostingTechStack;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class JobPostingResponse {

    // 등록된 채용공고에 대한 수정화면 DTO
    @Data
    public static class UpdateDTO {
        private List<String> positionTypes;
        private List<String> techStackCodes;
        private List<RegionDTO> regions;
        private List<String> careerLevels;
        private List<String> educationLevels;
        private DetailDTO detailDTO;

        public UpdateDTO(List<String> positionTypes, List<String> techStackCodes, List<RegionDTO> regions, List<String> careerLevels, List<String> educationLevels, DetailDTO detailDTO) {
            this.positionTypes = positionTypes;
            this.techStackCodes = techStackCodes;
            this.regions = regions;
            this.careerLevels = careerLevels;
            this.educationLevels = educationLevels;
            this.detailDTO = detailDTO;
        }
    }

    // 등록되어 있는 채용공고의 데이터를 담는 DTO
    @Data
    public static class DetailDTO {
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
        private List<String> techStackCodes;

        public DetailDTO(JobPosting jobPosting) {
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
            this.techStackCodes = jobPosting.getJobPostingTechStacks().stream()
                    .map(jpts -> jpts.getTechStack())
                    .toList();
        }
    }

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

    // 전체/진행중/마감된 리스트 조회
    @Data
    public static class ListDTO {
        private Integer jobPostingId;
        private String title;

        public ListDTO(Integer jobPostingId, String title) {
            this.jobPostingId = jobPostingId;
            this.title = title;
        }
    }

    // 구직자 - 채용공고 목록
    @Data
    public static class ItemDTO {
        private Integer id;
        private String companyName;
        private String title;
        private LocalDate deadline;
        private int dDay;
        private String career;
        private String address; // ← 외부에서 주입
        private String image;
        private boolean isBookmarked;
        private List<CompanyInfoResponse.TechStackDTO> techStacks;

        // address는 외부에서 전달받음
        public ItemDTO(JobPosting jobPostings, List<JobPostingTechStack> techStacks, String address, String image, String companyName, boolean isBookmarked) {
            this.id = jobPostings.getId();
            this.title = jobPostings.getTitle();
            this.deadline = jobPostings.getDeadline();
            this.dDay = calculateDDay(deadline);
            this.career = formatCareer(jobPostings.getMinCareerLevel(), jobPostings.getMaxCareerLevel());
            this.address = address;
            this.image = image;
            this.companyName = companyName;
            this.techStacks = techStacks.stream()
                    .map(stack -> new CompanyInfoResponse.TechStackDTO(stack.getTechStack()))
                    .collect(Collectors.toList());

            this.isBookmarked = isBookmarked;
        }

        private int calculateDDay(LocalDate deadline) {
            return (int) java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), deadline);
        }

        private String formatCareer(CareerLevel min, CareerLevel max) {
            if (min == null || max == null) return "경력무관";
            if (min.value == 0 && max.value == 0) return "신입";
            if (min.value > max.value) return "경력 정보 오류";
            if (min == max) {
                return min.label;
            } else {
                return min.label + " ~ " + max.label;
            }
        }
    }

    @Data
    public static class FilteredListDTO {
        private List<String> positions;
        private List<String> techStacks;
        private List<RegionResponse.DTO> regions;
        private List<CareerLevel> careerLevels;
        private List<JobPostingResponse.ItemDTO> jobPostingList;

        public FilteredListDTO(List<String> positions, List<String> techStacks, List<RegionResponse.DTO> regions, List<CareerLevel> careerLevels, List<ItemDTO> jobPostingList) {
            this.positions = positions;
            this.techStacks = techStacks;
            this.regions = regions;
            this.careerLevels = careerLevels;
            this.jobPostingList = jobPostingList;
        }
    }


    // 구직자 - 메인페이지 채용공고 목록
    @Data
    public static class MainDTO {

        private List<RecentDTO> recent; // 최신공고 3개
        private List<PopularDTO> popular; // 인기공고 8개

        @Data
        public static class RecentDTO {
            private Integer id;
            private String title;
            private String companyName;
            private String image;
            private boolean isActive;
            private BookmarkStatus bookmarkStatus;

            public RecentDTO(JobPosting jp, CompanyInfo companyInfo, String imageString, boolean isActive, boolean isBookmarked) {
                this.id = jp.getId();
                this.title = jp.getTitle();
                this.companyName = companyInfo.getCompanyName();
                this.image = imageString;
                this.isActive = isActive;
                this.bookmarkStatus = isBookmarked
                        ? BookmarkStatus.BOOKMARKED
                        : BookmarkStatus.NOT_BOOKMARKED;
            }
        }

        @Data
        public static class PopularDTO {
            private Integer id;
            private String title;
            private String companyName;
            private String image;
            private String address;
            private String career;
            private int dDay;
            private int viewCount;
            private BookmarkStatus bookmarkStatus;
            private List<CompanyInfoResponse.DetailDTO.JobPostingDTO.TechStackDTO> techStacks;

            public PopularDTO(JobPosting jp,
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
                this.bookmarkStatus = isBookmarked
                        ? BookmarkStatus.BOOKMARKED
                        : BookmarkStatus.NOT_BOOKMARKED;
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


        public MainDTO(List<RecentDTO> recent, List<PopularDTO> popular) {
            this.recent = recent;
            this.popular = popular;
        }
    }

    // 진행중/마감된 리스트 조회
    @Data
    public static class companyListDTO {
        private Integer jobPostingId;
        private String title;
        private LocalDate deadLine;
        private boolean isOpen;

        public companyListDTO(Integer jobPostingId, String title, LocalDate deadLine) {
            this.jobPostingId = jobPostingId;
            this.title = title;
            this.deadLine = deadLine;
            this.isOpen = deadLine.isAfter(LocalDate.now());
        }
    }

    // 기업 - 채용공고 리스트 보기 화면 DTO
    @Data
    public static class ListByStatusDTO {
        private List<JobPostingResponse.companyListDTO> openPostings;
        private List<JobPostingResponse.companyListDTO> closedPostings;
        private int openCount;
        private int closedCount;

        public ListByStatusDTO(List<JobPostingResponse.companyListDTO> openPostings,
                               List<JobPostingResponse.companyListDTO> closedPostings) {
            this.openPostings = openPostings;
            this.closedPostings = closedPostings;
            this.openCount = openPostings.size();
            this.closedCount = closedPostings.size();
        }
    }


}