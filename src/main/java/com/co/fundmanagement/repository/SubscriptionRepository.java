package com.co.fundmanagement.repository;

import com.co.fundmanagement.model.Subscription;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface SubscriptionRepository extends ReactiveMongoRepository<Subscription, String> {
    Mono<Subscription> findByUserIdAndFundIdAndStatus(String userId, String fundId, String status);
    Mono<Subscription> findByUserIdAndFundId(String userId, String fundId);
    Mono<Subscription> findByIdAndUserId(String id, String userId);
}
