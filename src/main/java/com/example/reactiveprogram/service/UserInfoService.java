package com.example.reactiveprogram.service;


import com.example.reactiveprogram.domain.UserInfo;
import com.example.reactiveprogram.repository.UserInfoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserInfoService {

    private UserInfoRepository userInfoRepository;

    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public Mono<UserInfo> addUserInfo(UserInfo userInfo) {

        return userInfoRepository.save(userInfo);
    }

    public Flux<UserInfo> getAllUserInfos() {
        return userInfoRepository.findAll();

    }

    public Mono<UserInfo> getUserInfoById(String id) {
        return userInfoRepository.findById(id);
    }

    public Mono<UserInfo> updateUserInfo(UserInfo updatedUserInfo, String id) {

        return userInfoRepository.findById(id)
                .flatMap(userInfo -> {
                    userInfo.setFirstName(updatedUserInfo.getFirstName());
                    userInfo.setLastName(updatedUserInfo.getLastName());
                    userInfo.setBirthDate(updatedUserInfo.getBirthDate());
                    return userInfoRepository.save(userInfo);
                });
    }
}
