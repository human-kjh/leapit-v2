package com.example.leapit.resume.training;

import com.example.leapit.resume.Resume;
import com.example.leapit.resume.ResumeRequest;
import com.example.leapit.resume.training.techstack.TrainingTechStackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final TrainingTechStackService trainingTechStackService;

    @Transactional
    public void update(Resume resumePS, @Valid List<ResumeRequest.UpdateDTO.TrainingDTO> trainingDTOs) {
        List<Training> trainings = resumePS.getTrainings();

        // 요청에서 id가 있는 DTO들만 Map으로 변환
        Map<Integer, ResumeRequest.UpdateDTO.TrainingDTO> dtoMap = trainingDTOs.stream()
                .filter(dto -> dto.getId() != null)
                .collect(Collectors.toMap(ResumeRequest.UpdateDTO.TrainingDTO::getId, dto -> dto));

        // 1. 기존 Training 리스트 돌면서 수정 or 삭제
        Iterator<Training> iterator = trainings.iterator();
        while (iterator.hasNext()) {
            Training training = iterator.next();
            ResumeRequest.UpdateDTO.TrainingDTO dto = dtoMap.remove(training.getId());

            if (dto != null) {
                // 수정 대상이면 update
                training.update(
                        dto.getStartDate(),
                        dto.getEndDate(),
                        dto.getIsOngoing(),
                        dto.getCourseName(),
                        dto.getInstitutionName(),
                        dto.getDescription()
                );

                // 연결된 TechStack들도 같이 수정
                trainingTechStackService.update(training, dto.getTechStacks());
            } else {
                // 요청에 없으면 삭제
                iterator.remove();
            }
        }

        // 2. 요청 데이터 중 id가 없는 것은 신규 추가
        for (ResumeRequest.UpdateDTO.TrainingDTO dto : trainingDTOs) {
            if (dto.getId() == null) {
                Training newTraining = Training.builder()
                        .resume(resumePS)
                        .startDate(dto.getStartDate())
                        .endDate(dto.getEndDate())
                        .isOngoing(dto.getIsOngoing())
                        .courseName(dto.getCourseName())
                        .institutionName(dto.getInstitutionName())
                        .description(dto.getDescription())
                        .build();
                trainings.add(newTraining);

                trainingTechStackService.update(newTraining, dto.getTechStacks());
            }
        }
    }
}
