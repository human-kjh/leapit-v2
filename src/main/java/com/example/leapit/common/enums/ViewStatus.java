package com.example.leapit.common.enums;

public enum ViewStatus {
    VIEWED("열람"),
    UNVIEWED("미열람");

    private final String label;

    ViewStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}