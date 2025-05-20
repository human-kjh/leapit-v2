package com.example.leapit.common.enums;

public enum SortType {
    POPULAR("인기순"),
    LATEST("최신순");

    private final String label;

    SortType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    // Optional: 기본값 제공
    public static SortType defaultValue() {
        return LATEST;
    }
}
