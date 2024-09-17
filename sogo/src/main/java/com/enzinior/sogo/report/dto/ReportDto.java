package com.enzinior.sogo.report.dto;

import com.enzinior.sogo.user.entity.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

public class ReportDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {

        @NotNull
        private Integer reportType;
        @NotBlank
        private String content;
        @NotNull
        private Long targetId;
        @NotNull
        private Boolean processed;
        @NotBlank
        private String userUuid;

    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private long reportId;
        private int reportType;
        private String content;
        private long targetId;
        private boolean processed;
        private String userNickname;
        private String userUuid;
    }

}
