package com.co.fundmanagement.service.impl;

import com.co.fundmanagement.exception.SubscriptionException;
import com.co.fundmanagement.model.Subscription;
import com.co.fundmanagement.repository.SubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Date;

import static org.mockito.Mockito.when;

class SubscriptionServiceTest {
    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionService subscriptionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllByUserId() {
        String id = "2541";

        when(subscriptionRepository.findAllByUserId(id)).thenReturn(Flux.just(builderSubscription()));

        Flux<Subscription> result = subscriptionService.findAllByUserId(id);
        StepVerifier.create(result)
                .expectNextMatches(user -> user.getId().equals(id) && user.getStatus().equals("ACTIVE"))
                .verifyComplete();
    }

    @Test
    public void testFindAllByUserIdNotSubscription() {
        String userId = "2";

        when(subscriptionRepository.findAllByUserId(userId)).thenReturn(Flux.empty());

        Flux<Subscription> result = subscriptionService.findAllByUserId(userId);
        StepVerifier.create(result)
                .expectError(SubscriptionException.class)
                .verify();
    }

    public Subscription builderSubscription() {
        return Subscription.builder()
                .id("2541")
                .fundId("252525")
                .openingDate(new Date())
                .cancellationDate(new Date())
                .status("ACTIVE")
                .initialValue(5000.0)
                .build();
    }
}