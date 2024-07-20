package com.enzinior.sogo.user.service;

import com.enzinior.sogo.user.dto.UserDto;
import com.enzinior.sogo.user.entity.User;
import jakarta.validation.Valid;

public interface UserService {

    void logout();

    User signUp(User user);

    User findUser(String uuid);

    boolean isNicknameAvailable(String nickname);

    User updateUser(UserDto.@Valid Patch user);

    void deleteUser(String uuid);

    void banUser(String uuid);

//    List<Badge> getBadges(String uuid);



}
