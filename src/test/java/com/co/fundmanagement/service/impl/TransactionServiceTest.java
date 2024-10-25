package com.co.fundmanagement.service.impl;

import com.co.fundmanagement.dto.RequestTransaction;
import com.co.fundmanagement.enums.SubscriptionStatusEnum;
import com.co.fundmanagement.exception.TransactionException;
import com.co.fundmanagement.model.*;
import com.co.fundmanagement.repository.FundRepository;
import com.co.fundmanagement.repository.SubscriptionRepository;
import com.co.fundmanagement.repository.TransactionRepository;
import com.co.fundmanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private FundRepository fundRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTransaction_UserNotFound() {
        RequestTransaction request = RequestTransaction.builder()
                .userId(UUID.randomUUID().toString())
                .build();

        when(userRepository.findById(request.getUserId())).thenReturn(Mono.empty());

        Mono<Response> result = transactionService.createTransaction(request);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getCode().equals(404))
                .verifyComplete();
    }

    @Test
    public void testCreateTransaction_FundNotFound() {
        RequestTransaction request = RequestTransaction.builder()
                .userId(UUID.randomUUID().toString())
                .build();

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .fullName("AMV")
                .build();

        when(userRepository.findById(request.getUserId())).thenReturn(Mono.just(user));
        when(fundRepository.findById(request.getFundId())).thenReturn(Mono.empty());

        Mono<Response> result = transactionService.createTransaction(request);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getCode().equals(404))
                .verifyComplete();
    }

    @Test
    public void testFindAllByUserId_NoTransactionsFound() {
        String userId = "user-id-no-transactions";

        when(transactionRepository.findByUserId(userId)).thenReturn(Flux.empty());

        Flux<Transaction> result = transactionService.findAllByUserId(userId);

        StepVerifier.create(result)
                .expectError(TransactionException.class)
                .verify();
    }

    @Test
    public void testCreateTransaction_SuccessfulOpening() {
        RequestTransaction request = RequestTransaction.builder()
                .userId("user1")
                .fundId("fund1")
                .transactionType("OPENING")
                .initialValue(1000.0)
                .build();

        Fund fund = Fund.builder()
                .id(request.getFundId())
                .minInitialValue(500.0)
                .name("name fund")
                .build();

        User user = User.builder()
                .id("user1")
                .fullName("AMV")
                .availableBalance(100000.0)
                .email("amv@gmail.com")
                .build();


        when(userRepository.findById(request.getUserId())).thenReturn(Mono.just(user));
        when(subscriptionRepository.findByUserIdAndFundIdAndStatus(
                request.getUserId(),
                request.getFundId(),
                "ACTIVE"))
                .thenReturn(Mono.empty());

        when(fundRepository.findById(request.getFundId())).thenReturn(Mono.just(fund));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(Mono.just(new Subscription()));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(Mono.just(new Transaction()));

        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        doReturn(Mono.empty())
                .when(emailService)
                .sendEmail(anyString());

        Mono<Response> result = transactionService.createTransaction(request);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getCode().equals(200))
                .verifyComplete();

        verify(userRepository).findById(request.getUserId());
    }

    @Test
    public void testCreateTransaction_SuccessfulCancellation() {
        RequestTransaction request = RequestTransaction.builder()
                .userId("user1")
                .fundId("fund1")
                .transactionType("CANCELLATION")
                .subscriptionId("subscriptionId")
                .build();

        Fund fund = Fund.builder()
                .id(request.getFundId())
                .minInitialValue(500.0)
                .name("name fund")
                .build();

        User user = User.builder()
                .id("user1")
                .fullName("AMV")
                .availableBalance(100000.0)
                .email("amv@gmail.com")
                .build();

        Subscription subscription = Subscription.builder()
                .id(request.getSubscriptionId())
                .initialValue(500000.0)
                .status(SubscriptionStatusEnum.ACTIVE.name())
                .build();

        when(userRepository.findById(request.getUserId())).thenReturn(Mono.just(user));
        when(subscriptionRepository.findByIdAndUserId(request.getSubscriptionId(), user.getId()))
                .thenReturn(Mono.just(subscription));
        when(fundRepository.findById(request.getFundId())).thenReturn(Mono.just(fund));
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(Mono.just(subscription));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));
        when(fundRepository.save(any(Fund.class))).thenReturn(Mono.just(fund));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(Mono.just(new Transaction()));

        Mono<Response> result = transactionService.createTransaction(request);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getCode().equals(200))
                .verifyComplete();
    }

    @Test
    public void subscriptionException_Active_Subscription() {
        RequestTransaction request = RequestTransaction.builder()
                .userId("user1")
                .fundId("fund1")
                .transactionType("OPENING")
                .initialValue(1000.0)
                .build();

        Subscription subscription = Subscription.builder()
                .id(request.getSubscriptionId())
                .initialValue(500000.0)
                .status(SubscriptionStatusEnum.ACTIVE.name())
                .build();

        User user = User.builder()
                .id("user1")
                .fullName("AMV")
                .availableBalance(100000.0)
                .email("amv@gmail.com")
                .build();

        when(userRepository.findById(request.getUserId())).thenReturn(Mono.just(user));
        when(subscriptionRepository.findByUserIdAndFundIdAndStatus(request.getUserId(), request.getFundId(), "ACTIVE"))
                .thenReturn(Mono.just(subscription));

        Mono<Response> result = transactionService.createTransaction(request);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getCode().equals(404))
                .verifyComplete();
    }

    @Test
    public void validateArgumentsException() {
        RequestTransaction request = RequestTransaction.builder()
                .userId("user1")
                .fundId("fund1")
                .transactionType("NN")
                .build();

        User user = User.builder()
                .id("fundId")
                .fullName("AMV")
                .build();

        when(userRepository.findById(request.getUserId())).thenReturn(Mono.just(user));

        Mono<Response> result = transactionService.createTransaction(request);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getCode().equals(404))
                .verifyComplete();
    }

    @Test
    public void validateSubscriptionException() {
        RequestTransaction request = RequestTransaction.builder()
                .userId("user1")
                .fundId("fund1")
                .transactionType("CANCELLATION")
                .build();

        User user = User.builder()
                .id("fundId")
                .fullName("AMV")
                .build();

        when(userRepository.findById(request.getUserId())).thenReturn(Mono.just(user));

        Mono<Response> result = transactionService.createTransaction(request);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getCode().equals(404))
                .verifyComplete();
    }
}
