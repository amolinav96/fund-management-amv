package com.co.fundmanagement.service.impl;

import com.co.fundmanagement.exception.SubscriptionException;
import com.co.fundmanagement.model.Subscription;
import com.co.fundmanagement.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.co.fundmanagement.enums.ErrorEnum.SUBSCRIPTION_NOT_FOUND;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Flux<Subscription> findAllByUserId(String userId) {
        return subscriptionRepository.findAllByUserId(userId)
                .switchIfEmpty(Mono.error(new SubscriptionException(SUBSCRIPTION_NOT_FOUND.getMessage())));
    }
}
