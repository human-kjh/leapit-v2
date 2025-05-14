package com.example.leapit.common.techstack;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class TechStackRepository {
    private final EntityManager em;

    public Optional<TechStack> findByCode(String code) {
        TechStack techStack = em.find(TechStack.class, code);
        return Optional.ofNullable(techStack);
    }
}
