package com.example.leapit.resume.experience.techstack;

import com.example.leapit.resume.experience.Experience;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "experience_tech_stack_tb")
@Entity
public class ExperienceTechStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Experience experience;

    @Column(nullable = false)
    private String techStack;
}
