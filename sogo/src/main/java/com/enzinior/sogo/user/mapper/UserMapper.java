package com.enzinior.sogo.user.mapper;

import com.enzinior.sogo.user.dto.UserDto;
import com.enzinior.sogo.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

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
