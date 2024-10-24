package com.co.fundmanagement.service;

import reactor.core.publisher.Mono;

public interface IEmailService {
    Mono<Void> sendEmail(String toUser, String subject, String message);
}
