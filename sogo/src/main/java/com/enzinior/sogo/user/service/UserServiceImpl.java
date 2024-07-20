package com.enzinior.sogo.user.service;

import com.enzinior.sogo.user.dto.UserDto;
import com.enzinior.sogo.user.entity.User;
import com.enzinior.sogo.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Override
    public void logout() {

    }

    @Override
    public User signUp(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUser(String userUuid) {
        return findUserByUuid(userUuid);
    }

    @Override
    public boolean isNicknameAvailable(String nickname) {
        return !userRepository.existsByNickname(nickname);
    }

    @Override
    @Transactional
    public User updateUser(UserDto.@Valid Patch user) {
        try {
            User findUser = findUserByUuid(user.getUserUuid());
            findUser.changeNickname(user.getNickname());
            findUser.changeImg(user.getImg());
            findUser.changeSentence(user.getSentence());
            userRepository.save(findUser);
            return findUser;
        } catch (Exception e) {
            // 예외 처리
            throw new RuntimeException("Failed to update user", e);
        }
    }

    @Override
    @Transactional
    public void deleteUser(String userUuid) {
        User user = findUserByUuid(userUuid);
        try {
            User findUser = findUserByUuid(user.getUserUuid());
            findUser.changeEmail(user.getUserUuid());
            findUser.changeImg("");
            findUser.changeId(-user.getId());
            userRepository.save(findUser);
        } catch (Exception e) {
            // 예외 처리
            throw new RuntimeException("Failed to delete user", e);
        }
    }

    @Override
    public void banUser(String userUuid) {
        try {
            User findUser = findUserByUuid(userUuid);
            findUser.changeState(!findUser.isState());
            userRepository.save(findUser);
        } catch (Exception e) {
            // 예외 처리
            throw new RuntimeException("Failed to ban user", e);
        }
    }

//    @Override
//    public List<Badge> getBadges(String uuid) {
//        return ;
//    }

    public User findUserByUuid(String uuid) {
        Optional<User> optionalUser = userRepository.findByUserUuid(uuid);
        User findUser = optionalUser
                .orElseThrow( () -> new NullPointerException());
        return findUser;
    }

//    public static void copyNonNullProperties(Object src, Object target) {
//        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
//    }
//
//    public static String[] getNullPropertyNames (Object source) {
//        final BeanWrapper src = new BeanWrapperImpl(source);
//        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
//
//        Set<String> emptyNames = new HashSet<>();
//        for(java.beans.PropertyDescriptor pd : pds) {
//            Object srcValue = src.getPropertyValue(pd.getName());
//            if (srcValue == null) emptyNames.add(pd.getName());
//        }
//        String[] result = new String[emptyNames.size()];
//        return emptyNames.toArray(result);
//    }

}
