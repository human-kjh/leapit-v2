package com.example.leapit.resume;

import lombok.Data;
import java.util.List;

public class ResumeResponse {
    
    @Data
    public static class DTO { // TODO : 이력서 및 항목 다 보이는 형태여야 한다.
        private Integer id;
        private Integer userId;
        private String title;
        private String photoUrl;
        private String summary;
        private String positionType;
        private String selfIntroduction;
        private String createdAt;
        private String updatedAt;

        public DTO(Resume resume) {
            this.id = resume.getId();
            this.userId = resume.getUser().getId();
            this.title = resume.getTitle();
            this.photoUrl = resume.getPhotoUrl();
            this.summary = resume.getSummary();
            this.positionType = resume.getPositionType();
            this.selfIntroduction = resume.getSelfIntroduction();
            this.createdAt = resume.getCreatedAt().toString();
            this.updatedAt = resume.getUpdatedAt().toString();
        }
    }

    @Data
    public static class ListDTO{
        private List<DTO> resumes; // TODO : 여기 담기는 DTO는 item이든 뭐든 해서 다르게 내가 원하는 값만 담긴 DTO로 변경해야한다. DTO는 entity 전체와 비슷하기에 불필요한 값까지 주게 된다

        public ListDTO(List<Resume> resumes) {
            this.resumes = resumes.stream().map(resume -> new DTO(resume)).toList();
        }
    }
}
