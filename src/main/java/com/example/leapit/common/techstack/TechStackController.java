package com.example.leapit.common.techstack;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TechStackController {
    private final TechStackService techStackService;
}
