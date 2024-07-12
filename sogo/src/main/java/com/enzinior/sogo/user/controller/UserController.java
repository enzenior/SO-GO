package com.enzinior.sogo.user.controller;

import com.enzinior.sogo.user.dto.UserDto;
import com.enzinior.sogo.user.entity.User;
import com.enzinior.sogo.user.mapper.UserMapper;
import com.enzinior.sogo.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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

//    @PostMapping
//    public ResponseEntity<?> logout () {
//        ResponseEntity<?> response = userService.logout();
//        return response;
//    }

    // 회원 등록
    @PostMapping("")
    public ResponseEntity<?> postUser(@RequestBody @Valid UserDto.SignUp user) {
        User postedUser = userService.postUser(userMapper.userSignUpToUser(user));

        System.out.println(user.getNickname());

        if(postedUser == null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(postedUser);
    }

    // 회원정보 조회
    @GetMapping("/{user-uuid}")
    public ResponseEntity<?> findUser(@PathVariable("user-uuid") String uuid) {
        User user = userService.findUser(uuid);

        if(user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(userMapper.userToUserResponse(user));
    }

    // 닉네임 중복 확인
    @GetMapping("/")
    public ResponseEntity<?> findUserByNickname(@RequestParam("nickname") String nickname) {
        User user = userService.findUserByNickname(nickname);

        if(user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("있어요~");
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("없어요~");
    }

    // 회원 정보 수정
    @PatchMapping("/{user-uuid}")
    public ResponseEntity<?> patchUser(@PathVariable("user-uuid") String uuid, @Valid
    @RequestBody UserDto.Patch requestBody) {
        requestBody.setUserUuid(uuid);
        User user = userMapper.userPatchToUser(requestBody);
        User updatedUser = userService.updateUser(user);

        return ResponseEntity.ok(updatedUser);
    }

    // 회원 정보 삭제
    @DeleteMapping("/{user-uuid}")
    public ResponseEntity<?> deleteUser(@PathVariable("user-uuid") String uuid) {
        userService.deleteUser(uuid);

        return ResponseEntity.noContent().build();
    }

//    @GetMapping("{user-uuid}/collections")
//    public ResponseEntity<?> getBadges(PathVariable("user-uuid") String uuid) {
//        List<Badge> badges = userService.getBadges(uuid);
//
//        return new ResponseEntity<T>(badges, HttpStatus.OK);
//    }
}
