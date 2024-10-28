package com.co.fundmanagement.controller;

import com.co.fundmanagement.model.Subscription;
import com.co.fundmanagement.service.impl.SubscriptionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Date;

import static org.mockito.Mockito.when;

public class SubscriptionControllerTest {

    @InjectMocks
    private SubscriptionController subscriptionController;

    @Mock
    private SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllByUserId() {
        // Given
        String userId = "user123";
        Subscription subscription1 = new Subscription("1", "user123", "fund456", "active", new Date(), null, 1000.0);
        Subscription subscription2 = new Subscription("2", "user123", "fund789", "inactive", new Date(), null, 2000.0);
        Flux<Subscription> subscriptions = Flux.just(subscription1, subscription2);

        when(subscriptionService.findAllByUserId(userId)).thenReturn(subscriptions);

        // When
        Flux<Subscription> result = subscriptionController.findAllByUserId(userId);

        // Then
        StepVerifier.create(result)
                .expectNext(subscription1)
                .expectNext(subscription2)
                .verifyComplete();
    }
}
