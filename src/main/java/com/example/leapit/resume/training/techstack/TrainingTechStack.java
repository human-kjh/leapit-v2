package com.example.leapit.resume.training.techstack;

import com.example.leapit.resume.training.Training;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "training_tech_stack_tb")
@Entity
public class TrainingTechStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_id", nullable = false)
    private Training training;

    @Column(nullable = false)
    private String techStack;

    @Builder
    public TrainingTechStack(Integer id, Training training, String techStack) {
        this.id = id;
        this.training = training;
        this.techStack = techStack;
    }
}
