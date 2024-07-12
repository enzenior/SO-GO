package com.enzinior.sogo.user.service;

import com.enzinior.sogo.user.dto.UserDto;
import com.enzinior.sogo.user.entity.User;
import com.enzinior.sogo.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.mbeans.MBeanUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Override
    public void logout() {

    }

    @Override
    public User postUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUser(String userUuid) {
        return findUserByUuid(userUuid);
    }

    @Override
    public User findUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        try {
            User findUser = findUserByUuid(user.getUserUuid());
            findUser.setNickname(user.getNickname());
            findUser.setImg(user.getImg());
            findUser.setSentence(user.getSentence());
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
        User findUser = findUserByUuid(userUuid);
        userRepository.delete(findUser);
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
