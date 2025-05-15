package com.example.leapit.board;

import lombok.Data;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class BoardResponse {

    @Data
    public static class DTO {
        private Integer id;
        private String title;
        private String content;
        private String name;
        private Timestamp createdAt;
        private String createdAtFormatted;

        public DTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.name = board.getUser().getName();
            this.createdAt = board.getCreatedAt();
            this.createdAtFormatted = board.getCreatedAt()
                    .toLocalDateTime()
                    .format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        }

    }
}
