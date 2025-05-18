package com.example.leapit.jobposting;

import com.example.leapit.common.enums.CareerLevel;
import com.example.leapit.common.enums.SortType;
import lombok.Data;

public class JobPostingRequest {

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
                    if (careerLabel.trim().equals(level.getLabel())) {
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
