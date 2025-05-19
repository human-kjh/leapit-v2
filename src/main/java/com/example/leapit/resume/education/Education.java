package com.example.leapit.resume.education;

import com.example.leapit.common.enums.EducationLevel;
import com.example.leapit.resume.Resume;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Table(name = "education_tb")
@Entity
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    private LocalDate graduationDate;

    private Boolean isDropout = false;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EducationLevel educationLevel;

    @Column(nullable = false)
    private String schoolName;

    private String major;

    @Column(precision = 3, scale = 2)
    private BigDecimal gpa;

    @Column(precision = 2, scale = 1)
    private BigDecimal gpaScale;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder

    public Education(Integer id, Resume resume, LocalDate graduationDate, Boolean isDropout, EducationLevel educationLevel, String schoolName, String major, BigDecimal gpa, BigDecimal gpaScale, Timestamp createdAt) {
        this.id = id;
        this.resume = resume;
        this.graduationDate = graduationDate;
        this.isDropout = isDropout;
        this.educationLevel = educationLevel;
        this.schoolName = schoolName;
        this.major = major;
        this.gpa = gpa;
        this.gpaScale = gpaScale;
        this.createdAt = createdAt;
    }
}