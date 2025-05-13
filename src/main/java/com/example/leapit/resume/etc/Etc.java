package com.example.leapit.resume.etc;

import com.example.leapit.resume.Resume;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Table(name = "etc_tb")
@Entity
public class Etc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    private LocalDate startDate;
    private LocalDate endDate;
    //hasEndDate = true : 종료일 있음
    private Boolean hasEndDate = true;
    private String title;
    private String etcType;
    private String institutionName;

    @Lob
    private String description;

    @CreationTimestamp
    private Timestamp createdAt;
}
