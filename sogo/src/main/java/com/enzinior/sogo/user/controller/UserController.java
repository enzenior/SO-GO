package com.enzinior.sogo.user.controller;

import com.enzinior.sogo.user.dto.UserDto;
import com.enzinior.sogo.user.entity.User;
import com.enzinior.sogo.user.mapper.UserMapper;
import com.enzinior.sogo.user.service.MapsService;
import com.enzinior.sogo.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final MapsService mapsService;

//    @PostMapping
//    public ResponseEntity<?> logout () {
//        ResponseEntity<?> response = userService.logout();
//        return response;
//    }

    // 회원 등록
    @PostMapping("")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserDto.SignUp userDto) {
        userService.verifyExistsUser(userMapper.userSignUpToUser(userDto));

        return ResponseEntity.ok(userService.signUp(userMapper.userSignUpToUser(userDto)));
    }

    // 회원정보 조회
    @GetMapping("/{user-uuid}")
    public ResponseEntity<?> findUser(@PathVariable("user-uuid") String uuid) {
        User user = userService.findUser(uuid);

        return ResponseEntity.ok(userMapper.userToUserResponse(user));
    }

    // 닉네임 중복 확인
    @GetMapping("")
    public ResponseEntity<?> findUserByNickname(@RequestParam("nickname") String nickname) {
        userService.verifyNicknameAvailable(nickname);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 회원 지도 조회
    @GetMapping("/{user-uuid}/maps")
    public ResponseEntity<?> findUserMaps(@PathVariable("user-uuid") String uuid) {
        return ResponseEntity.ok(mapsService.getMaps(uuid));
    }

    // 회원 정보 수정
    @PatchMapping("/{user-uuid}")
    public ResponseEntity<?> patchUser(@PathVariable("user-uuid") String uuid, @Valid
    @RequestBody UserDto.Patch requestBody) {
        requestBody.setUserUuid(uuid);
        User updatedUser = userService.updateUser(requestBody);

        return ResponseEntity.ok(userMapper.userToUserResponse(updatedUser));
    }

    // 회원 밴
    @PatchMapping("/ban/{user-uuid}")
    public ResponseEntity<?> banUser(@PathVariable("user-uuid") String uuid) {
        userService.banUser(uuid);

        return ResponseEntity.noContent().build();
    }

    // 회원 정보 삭제
    @DeleteMapping("/{user-uuid}")
    public ResponseEntity<?> deleteUser(@PathVariable("user-uuid") String uuid) {
        userService.deleteUser(uuid);

        return ResponseEntity.noContent().build();
    }
}
