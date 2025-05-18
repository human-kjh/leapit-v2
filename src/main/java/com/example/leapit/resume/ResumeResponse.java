package com.example.leapit.resume;

import com.example.leapit.user.User;
import lombok.Data;
import java.util.List;

public class ResumeResponse {
    
    /*
    @Data
    public static class DTO { // TODO : 이력서 및 항목 다 보이는 형태여야 한다.
        private Integer id;
        private String title;
        private String photoUrl;

        // 유저 정보
        private String name;
        private String email;
        private Integer birthDate;
        private String contactNumber;

        private String summary;
        private String positionType;
        private String selfIntroduction;

        private String createdAt;
        private String updatedAt;

        private List<String> resumeTechStacks;
        private List<EducationDTO> educations;
        private List<ProjectDTO> projects;
        private List<ExperienceDTO> experiences;
        private List<LinkDTO> links;
        private List<TrainingDTO> trainings;
        private List<EtcDTO> etcs;

        public DTO(Resume resume) {
            this.id = resume.getId();
            this.title = resume.getTitle();
            this.photoUrl = resume.getPhotoUrl();

            this.name = resume.getUser().getName();
            this.email = resume.getUser().getEmail();
            this.birthDate = resume.getUser().getBirthDate().getYear();
            this.contactNumber = resume.getUser().getContactNumber();

            this.summary = resume.getSummary();
            this.positionType = resume.getPositionType();
            this.selfIntroduction = resume.getSelfIntroduction();
            this.createdAt = resume.getCreatedAt().toString();
            this.updatedAt = resume.getUpdatedAt().toString();
            this.resumeTechStacks = resume.getResumeTechStacks().stream()
                    .map(rt -> rt.getTechStack().getCode()).toList();
            this.educations = educations;
            this.projects = projects;
            this.experiences = experiences;
            this.links = links;
            this.trainings = trainings;
            this.etcs = etcs;
        }
    }
     */

    // 이력서 목록 return
    @Data
    public static class ListDTO{
        private List<ItemDTO> resumes; // TODO : 여기 담기는 DTO는 item이든 뭐든 해서 다르게 내가 원하는 값만 담긴 DTO로 변경해야한다. DTO는 entity 전체와 비슷하기에 불필요한 값까지 주게 된다

        public ListDTO(List<Resume> resumes) {
            this.resumes = resumes
                    .stream()
                    .map(resume -> new ItemDTO(resume))
                    .toList();
        }

        @Data
        public static class ItemDTO{
            private Integer id;
            private String title;
            private String updatedAt;

            public ItemDTO(Resume resume) {
                this.id = resume.getId();
                this.title = resume.getTitle();
                this.updatedAt = resume.getUpdatedAt().toString();
            }
        }
    }

    // 이력서 저장시 유저정보 + 선택지
    @Data
    public static class SaveDTO {
        private String name;
        private String email;
        private Integer birthDate; // 생년만 (e.g. 2000)
        private String contactNumber;
        // TODO : CareerLevel 추가
        private List<String> positionTypes;
        private List<String> techStacks;

        public SaveDTO(User user, List<String> positionTypes, List<String> techStacks) {
            this.name = user.getName();
            this.email = user.getEmail();
            this.birthDate = user.getBirthDate().getYear();
            this.contactNumber = user.getContactNumber();
            this.positionTypes = positionTypes;
            this.techStacks = techStacks;
        }
    }

}
