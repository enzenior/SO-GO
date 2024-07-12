package com.enzinior.sogo.user.service;

import com.enzinior.sogo.user.dto.UserDto;
import com.enzinior.sogo.user.entity.User;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;

public interface UserService {

    void logout();

    User postUser(User user);

    User findUser(String uuid);

    User findUserByNickname(String nickname);

    User updateUser(User user);

    void deleteUser(String uuid);

//    List<Badge> getBadges(String uuid);



}
