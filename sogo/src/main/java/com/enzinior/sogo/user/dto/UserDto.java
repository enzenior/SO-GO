package com.enzinior.sogo.user.dto;

import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


public class UserDto {

    @Getter
    @Setter
    @Builder
    public static class SignUp {
        @NotNull
        private long userId;
        @NotBlank
        private String nickname;
        @Email
        private String email;
        @NotNull
        private boolean site;
        @NotBlank
        private String role;
        private int report = 0;
        @NotBlank
        private String userUuid;
        private String sentence;
    }


    @Getter
    @Builder
    public static class Patch {
        @Setter
        private String userUuid;
        @NotBlank
        private String nickname;
        @NotBlank
        private String img;
        @NotBlank
        private String sentence;
    }

    @Getter
    @Builder
    public static class Response {
        private String nickname;
        private String img;
        private boolean admin;
        private String userUuid;
        private String sentence;
    }

    @Getter
    @Builder
    public static class Cookie {
        private String nickname;
        private String img;
        private boolean admin;
        private String userUuid;
    }

}
