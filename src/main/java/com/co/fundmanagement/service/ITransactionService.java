package com.co.fundmanagement.service;

import com.co.fundmanagement.dto.RequestTransaction;
import com.co.fundmanagement.model.Response;
import com.co.fundmanagement.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionService {
    Mono<Response> createTransaction(RequestTransaction requestTransaction);
    Flux<Transaction> findAllByUserId(String userId);
}
