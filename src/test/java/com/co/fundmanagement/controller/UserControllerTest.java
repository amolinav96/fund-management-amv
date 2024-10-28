package com.co.fundmanagement.controller;

import com.co.fundmanagement.model.User;
import com.co.fundmanagement.service.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById() {
        String userId = "user123";
        User expectedUser = new User(userId, "John Doe", "123456789", "Passport", 1500.0, "john.doe@example.com");

        when(userService.findById(anyString())).thenReturn(Mono.just(expectedUser));

        Mono<User> result = userController.findById(userId);

        StepVerifier.create(result)
                .expectNext(expectedUser)
                .verifyComplete();
    }
}
