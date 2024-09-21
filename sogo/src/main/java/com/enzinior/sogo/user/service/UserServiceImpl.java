package com.enzinior.sogo.user.service;

import com.enzinior.sogo.exception.BusinessLogicException;
import com.enzinior.sogo.exception.ExceptionCode;
import com.enzinior.sogo.user.dto.UserDto;
import com.enzinior.sogo.user.entity.User;
import com.enzinior.sogo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public void logout() {

    }

    @Override
    @Transactional
    public User signUp(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findUser(String userUuid) {
        return findUserByUuid(userUuid);
    }

    @Override
    @Transactional(readOnly = true)
    public void verifyExistsUser(User user) {
        isEmailAvailable(user.getEmail());
        isNicknameAvailable(user.getNickname());
    }

    @Override
    @Transactional(readOnly = true)
    public void verifyNicknameAvailable(String nickname) {
        isNicknameAvailable(nickname);
    }

    @Override
    @Transactional
    public User updateUser(UserDto.Patch user) {
        try {
            User findUser = findUserByUuid(user.getUserUuid());
            findUser.changeNickname(user.getNickname());
            findUser.changeImg(user.getImg());
            findUser.changeSentence(user.getSentence());
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
            findUser.changeNickname(userUuid);
            findUser.changeEmail(userUuid);
            findUser.changeImg("");
            findUser.changeId("-" + user.getId());
        } catch (Exception e) {
            // 예외 처리
            throw new RuntimeException("Failed to delete user", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Integer> getMaps(String uuid) {
        User user = findUser(uuid);
        return user.getMaps();
    }

    @Override
    @Transactional
    public void updateMaps(User user, String address) {
        String[] split = address.split(" ");
        String newAddress = split[0] + " " + split[1];
        Map<String, Integer> maps = user.getMaps();
        maps.put(newAddress, maps.getOrDefault(newAddress, 0) + 1);
    }

    @Override
    @Transactional
    public void banUser(String userUuid) {
        try {
            User findUser = findUserByUuid(userUuid);
            findUser.changeState(!findUser.isState());
        } catch (Exception e) {
            // 예외 처리
            throw new RuntimeException("Failed to ban user", e);
        }
    }


    private User findUserByUuid(String uuid) {
        Optional<User> optionalUser = userRepository.findByUserUuid(uuid);
        User findUser = optionalUser
                .orElseThrow( () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        return findUser;
    }

    private void isNicknameAvailable(String nickname) {
        if(userRepository.existsByNickname(nickname)) {
            throw new BusinessLogicException(ExceptionCode.NICKNAME_EXIST);
        }
    }

    private void isEmailAvailable(String email) {
        if(userRepository.existsByEmail(email)) {
            throw new BusinessLogicException(ExceptionCode.EMAIL_EXIST);
        }
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
