package com.jasdom.user_module.user_module.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasdom.user_module.user_module.UserModuleApplication;
import com.jasdom.user_module.user_module.model.User;
import com.jasdom.user_module.user_module.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Objects;

@SpringBootTest(classes = UserModuleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    UserService service;

    private User user;

    @BeforeEach
    public void setUp() {
        user = service.findAll().get(1);
    }

    @Test
    public void testUserById() throws Exception{
        String expected = new ObjectMapper().writeValueAsString(user);

        WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build()
                .get()
                .uri("/users/" + user.getId())
                .exchange()
                .expectStatus().isOk()			// 200
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody().json(expected);
    }

    @Test
    public void testAddUser() throws Exception{
        User added = new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius");
        String json = new ObjectMapper().writeValueAsString(added);

        var result = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build()
                .post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(json))
                .exchange()
                .expectStatus().isCreated()
                .returnResult(String.class);
        WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build()
                .get()
                .uri(Objects.requireNonNull(result.getResponseHeaders().getLocation()))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody()
                .jsonPath("$.name").isEqualTo(added.getName())
                .jsonPath("$.surname").isEqualTo(added.getSurname())
                .jsonPath("$.emailAddress").isEqualTo(added.getEmailAddress())
                .jsonPath("$.phoneNumber").isEqualTo(added.getPhoneNumber())
                .jsonPath("$.password").isEqualTo(added.getPassword())
                .jsonPath("$.address", added.getAddress());
    }
}
