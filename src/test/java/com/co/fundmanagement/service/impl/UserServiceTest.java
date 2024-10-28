package com.co.fundmanagement.service.impl;

import com.co.fundmanagement.exception.UserNotFoundException;
import com.co.fundmanagement.model.User;
import com.co.fundmanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByIdFound() {
        String userId = "2541";
        User mockFund = builderUser();

        when(userRepository.findById(userId)).thenReturn(Mono.just(mockFund));

        Mono<User> result = userService.findById(userId);
        StepVerifier.create(result)
                .expectNextMatches(user -> user.getId().equals(userId) && user.getFullName().equals("Aldahir Molina"))
                .verifyComplete();
    }

    @Test
    public void testFindByIdNotFound() {
        String userId = "2";

        when(userRepository.findById(userId)).thenReturn(Mono.empty());

        Mono<User> result = userService.findById(userId);
        StepVerifier.create(result)
                .expectError(UserNotFoundException.class)
                .verify();
    }

    public User builderUser() {
        return User.builder()
                .id("2541")
                .email("am@gmail.com")
                .availableBalance(500.0)
                .fullName("Aldahir Molina")
                .identificationNumber("1152702267")
                .identificationType("CC")
                .build();
    }

}