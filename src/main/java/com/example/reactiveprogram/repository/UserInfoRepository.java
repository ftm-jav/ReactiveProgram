package com.example.reactiveprogram.repository;


import com.example.reactiveprogram.domain.UserInfo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends ReactiveMongoRepository<UserInfo,String> {
}
