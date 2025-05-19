package com.example.leapit.jobposting;

import com.example.leapit.common.enums.CareerLevel;
import com.example.leapit.common.enums.SortType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(JobPostingRepository.class)
@DataJpaTest
public class JobPostingRepositoryTest {

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Test
    public void find_by_filter_test() {
        // given
        Integer regionId = null;
        Integer subRegionId = null;
        CareerLevel career = null; // 예: CareerLevel.JUNIOR
        String techStackCode = null; // 예: "JAVA"
        String positionLabel = null; // 예: "백엔드"
        SortType sortType = SortType.LATEST;
        Integer sessionUserId = 1;

        // when
        List<JobPostingResponse.JobPostingDTO> result = jobPostingRepository.findByFilter(
                regionId,
                subRegionId,
                career,
                techStackCode,
                positionLabel,
                sortType,
                sessionUserId
        );

        // eye
        System.out.println("====================================");
        for (var dto : result) {
            System.out.println(" 공고 ID: " + dto.getId());
            System.out.println(" - 제목: " + dto.getTitle());
            System.out.println(" - 마감일: " + dto.getDeadline());
            System.out.println(" - D-Day: D-" + dto.getDDay());
            System.out.println(" - 경력: " + dto.getCareer());
            System.out.println(" - 지역: " + dto.getAddress());
            System.out.println(" - 회사명: " + dto.getCompanyName());
            System.out.println(" - 이미지: " + dto.getImage());
            System.out.println(" - 북마크: " + (dto.isBookmarked() ? "O" : "X"));
            System.out.println(" - 기술 스택:");
            for (var stack : dto.getTechStacks()) {
                System.out.println("   • " + stack.getName()); // 또는 getCode()
            }
            System.out.println("-----------------------------------");
        }
    }

}
