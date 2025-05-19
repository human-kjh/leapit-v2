package com.example.leapit.common.enums;

public enum EducationLevel {
    NO_PREFERENCE("무관", 0),
    HIGH_SCHOOL("고등학교", 1),
    ASSOCIATE("전문학사", 2),
    BACHELOR("학사", 3),
    MASTER("석사", 4),
    DOCTOR("박사", 5);

    public String label;
    public Integer value;

    EducationLevel(String label, Integer value) {
        this.label = label;
        this.value = value;
    }
}