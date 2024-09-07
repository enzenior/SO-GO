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
        private int reportType; // 0 : review, 1 : comment, 2 : place
        private String content;
        private long targetId; // id
        private boolean processed = Boolean.FALSE;
        private String userUuid; // 신고자
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
