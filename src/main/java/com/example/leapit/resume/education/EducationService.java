package com.example.leapit.resume.education;

import com.example.leapit.resume.Resume;
import com.example.leapit.resume.ResumeRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class EducationService {
    private final EducationRepository educationRepository;

    @Transactional
    public void update(Resume resumePS, @NotEmpty(message = "학력은 필수항목입니다.") @Valid List<ResumeRequest.UpdateDTO.EducationDTO> educationDTOs) {
        List<Education> educations = resumePS.getEducations();

        Map<Integer, ResumeRequest.UpdateDTO.EducationDTO> dtoMap = educationDTOs.stream()
                .filter(dto -> dto.getId() != null)
                .collect(Collectors.toMap(ResumeRequest.UpdateDTO.EducationDTO::getId, dto -> dto));

        Iterator<Education> iterator = educations.iterator();
        while (iterator.hasNext()) {
            Education education = iterator.next();
            ResumeRequest.UpdateDTO.EducationDTO dto = dtoMap.remove(education.getId());

            if (dto != null) {
                education.update(
                        dto.getGraduationDate(),
                        dto.getIsDropout(),
                        dto.getEducationLevel(),
                        dto.getSchoolName(),
                        dto.getMajor(),
                        dto.getGpa(),
                        dto.getGpaScale()
                );
            } else {
                iterator.remove();
            }
        }

        for (ResumeRequest.UpdateDTO.EducationDTO dto : educationDTOs) {
            if (dto.getId() == null) {
                Education newEducation = Education.builder()
                        .resume(resumePS)
                        .graduationDate(dto.getGraduationDate())
                        .isDropout(dto.getIsDropout())
                        .educationLevel(dto.getEducationLevel())
                        .schoolName(dto.getSchoolName())
                        .major(dto.getMajor())
                        .gpa(dto.getGpa())
                        .gpaScale(dto.getGpaScale())
                        .build();
                educations.add(newEducation);
            }
        }
    }
}
