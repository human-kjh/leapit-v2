package com.example.leapit.common.enums;

public enum JobPostingStatus {
    ALL("전체"),
    OPEN("진행중"),
    CLOSED("마감");

    private final String label;

    JobPostingStatus(String label) {
        this.label = label;
    }
}