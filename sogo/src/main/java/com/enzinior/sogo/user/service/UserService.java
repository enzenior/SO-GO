package com.enzinior.sogo.user.service;

import com.enzinior.sogo.user.dto.UserDto;
import com.enzinior.sogo.user.entity.User;

import java.util.Map;

public interface UserService {

    void logout();

    User signUp(User user);

    User findUser(String uuid);

    void verifyExistsUser(User user);

    User updateUser(UserDto.Patch user);

    void deleteUser(String uuid);

    void banUser(String uuid);

    void verifyNicknameAvailable(String nickname);

    Map<String, Integer> getMaps(String uuid);

    void updateMaps(User user, String address);
}
