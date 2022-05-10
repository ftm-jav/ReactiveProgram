package com.example.reactiveprogram.controller;

import com.example.reactiveprogram.domain.UserInfo;
import com.example.reactiveprogram.service.UserInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1.0.0")
public class UsersInfoController {

    private UserInfoService userInfoService;

    public UsersInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/userinfos")
    public Flux<UserInfo> getAllUserInfos(){
        return userInfoService.getAllUserInfos().log();
    }

    @GetMapping("/userinfos/{id}")
    public Mono<UserInfo> getMovieInfoById(@PathVariable String id){
        return userInfoService.getUserInfoById(id);
    }

    @PostMapping("/userinfos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserInfo> addUserInfo(@RequestBody UserInfo userInfo){
        return userInfoService.addUserInfo(userInfo).log();
    }

    @PutMapping("/Userinfos/{id}")
    public Mono<UserInfo> updateUserInfo(@RequestBody UserInfo updatedUserInfo, @PathVariable String id){
        return userInfoService.updateUserInfo(updatedUserInfo, id);
    }
}
