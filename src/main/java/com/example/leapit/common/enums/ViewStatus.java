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

    public static ViewStatus fromLabel(String label) {
        for (ViewStatus status : values()) {
            if (status.label.equals(label)) return status;
        }
        return null;
    }

    public Boolean toBoolean() {
        return this == VIEWED;
    }
}

