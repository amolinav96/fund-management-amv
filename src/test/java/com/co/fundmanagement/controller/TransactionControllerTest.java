package com.co.fundmanagement.controller;

import com.co.fundmanagement.dto.RequestTransaction;
import com.co.fundmanagement.model.Response;
import com.co.fundmanagement.model.Transaction;
import com.co.fundmanagement.service.impl.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTransaction() {
        RequestTransaction requestTransaction = new RequestTransaction(); // Asumiendo que RequestTransaction tiene un constructor vacío
        Response expectedResponse = new Response(200, "Transaction created successfully");

        when(transactionService.createTransaction(any(RequestTransaction.class))).thenReturn(Mono.just(expectedResponse));

        Mono<Response> result = transactionController.createTransaction(requestTransaction);

        StepVerifier.create(result)
                .expectNext(expectedResponse)
                .verifyComplete();
    }

    @Test
    public void testFindAllByUserId() {
        String userId = "user123";
        Transaction transaction1 = new Transaction("1", new Date(), userId, "sub456", "deposit");
        Transaction transaction2 = new Transaction("2", new Date(), userId, "sub789", "withdrawal");
        Flux<Transaction> transactions = Flux.just(transaction1, transaction2);

        when(transactionService.findAllByUserId(userId)).thenReturn(transactions);

        Flux<Transaction> result = transactionController.findAllByUserId(userId);

        StepVerifier.create(result)
                .expectNext(transaction1)
                .expectNext(transaction2)
                .verifyComplete();
    }
}
