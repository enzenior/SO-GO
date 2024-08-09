package com.enzinior.sogo.user.mapper;

import com.enzinior.sogo.user.dto.UserDto;
import com.enzinior.sogo.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    default User userPatchToUser(UserDto.Patch requestBody) {
        return new User(requestBody);
    }

    default User userSignUpToUser(UserDto.SignUp requestBody) {
        return new User(requestBody);
    }

    UserDto.Response userToUserResponse(User user);
}
