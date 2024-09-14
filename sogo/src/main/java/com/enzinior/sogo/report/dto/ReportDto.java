package com.enzinior.sogo.report.dto;

import com.enzinior.sogo.user.entity.User;
import lombok.*;

public class ReportDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        private int reportType;
        private String content;
        private long targetId;
        private boolean processed;
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