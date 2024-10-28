package com.co.fundmanagement.controller;

import com.co.fundmanagement.model.Subscription;
import com.co.fundmanagement.service.impl.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/subscription")
@CrossOrigin(origins = "*")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/allById")
    public Flux<Subscription> findAllByUserId(String userId) {
        return subscriptionService.findAllByUserId(userId);
    }
}
