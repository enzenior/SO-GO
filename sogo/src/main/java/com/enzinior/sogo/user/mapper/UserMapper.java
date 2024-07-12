package com.enzinior.sogo.user.mapper;

import com.enzinior.sogo.user.dto.UserDto;
import com.enzinior.sogo.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userPatchToUser(UserDto.Patch requestBody);

    User userSignUpToUser(UserDto.SignUp requestBody);

    UserDto.Response userToUserResponse(User user);
}
