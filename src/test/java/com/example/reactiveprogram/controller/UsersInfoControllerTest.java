package com.example.reactiveprogram.controller;

import com.example.reactiveprogram.domain.UserInfo;
import com.example.reactiveprogram.repository.UserInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("prod")
@EnableAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
class UsersInfoControllerTest {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    WebTestClient webTestClient;

    static String USERS_INFO_URL = "/v1.0.0/userinfos";

    @BeforeEach
    void setUp() {
        var userinfos = List.of(new UserInfo(null, "user1", "user1", LocalDate.parse("2000-06-10")),
                new UserInfo(null, "user2","user2", LocalDate.parse("1990-02-25")),
                new UserInfo("a", "user3","user3", LocalDate.parse("1999-09-30")));

        userInfoRepository.saveAll(userinfos)
                .blockLast();
    }

    @AfterEach
    void tearDown() {
        userInfoRepository.deleteAll().block();
    }

    @Test
    void addUserInfo() {

        var userInfo = new UserInfo(null, "user4","user4", LocalDate.parse("2000-01-15"));

        webTestClient
                .post()
                .uri(USERS_INFO_URL)
                .bodyValue(userInfo)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(UserInfo.class)
                .consumeWith(userInfoEntityExchangeResult -> {

                    var savedUserInfo = userInfoEntityExchangeResult.getResponseBody();
                    assert savedUserInfo!=null;
                    assert savedUserInfo.getUserId()!=null;
                });

    }

    @Test
    void getAllUserInfos() {

        webTestClient
                .get()
                .uri(USERS_INFO_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(UserInfo.class)
                .hasSize(3);

    }

    @Test
    void getUserInfoById() {

        var userInfoId = "a";
        webTestClient
                .get()
                .uri(USERS_INFO_URL+"/{id}", userInfoId)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$.firstName").isEqualTo("user3");

    }

    @Test
    void updateMovieInfo() {

        var userInfoId = "a";
        var userInfo = new UserInfo(null, "user311","user3", LocalDate.parse("2005-06-15"));

        webTestClient
                .put()
                .uri(USERS_INFO_URL+"/{id}", userInfoId)
                .bodyValue(userInfo)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(UserInfo.class)
                .consumeWith(userInfoEntityExchangeResult -> {
                    var updatedUserInfo = userInfoEntityExchangeResult.getResponseBody();
                    assert updatedUserInfo!=null;
                    assert updatedUserInfo.getUserId()!=null;
                    assertEquals("user311", updatedUserInfo.getFirstName());
                });

    }
}