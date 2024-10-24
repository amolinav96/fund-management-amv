package com.co.fundmanagement.service.impl;

import com.co.fundmanagement.dto.RequestTransaction;
import com.co.fundmanagement.exception.*;
import com.co.fundmanagement.model.*;
import com.co.fundmanagement.repository.FundRepository;
import com.co.fundmanagement.repository.SubscriptionRepository;
import com.co.fundmanagement.repository.TransactionRepository;
import com.co.fundmanagement.repository.UserRepository;
import com.co.fundmanagement.service.ITransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.co.fundmanagement.enums.ErrorEnum.*;
import static com.co.fundmanagement.enums.MessageEnum.SUBSCRIPTION_OPENING;
import static com.co.fundmanagement.enums.SubscriptionStatusEnum.ACTIVE;
import static com.co.fundmanagement.enums.TransactionTypeEnum.CANCELLATION;
import static com.co.fundmanagement.enums.TransactionTypeEnum.OPENING;
import static com.co.fundmanagement.factory.FactoryObject.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class TransactionService implements ITransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);


    @Override
    public Mono<Response> createTransaction(RequestTransaction request) {
        return userRepository.findById(request.getUserId())
                .switchIfEmpty(Mono.error(new UserNotFoundException(USER_NOT_FOUND.getMessage())))
                .flatMap(user -> handleSubscription(request)
                        .flatMap(subscription -> handleFund(request, subscription, user)))
                .onErrorResume(this::handleError);
    }

    private Mono<Response> handleFund(RequestTransaction request, Subscription subscription, User user) {
        return fundRepository.findById(request.getFundId())
                .switchIfEmpty(Mono.error(new FundNotFoundException(FUND_NOT_FOUND.getMessage())))
                .flatMap(fund -> OPENING.name().equals(request.getTransactionType()) ?
                        handleOpeningTransaction(request, fund, user) :
                        handleCancellationTransaction(request, subscription, fund, user));
    }

    private Mono<Response> handleCancellationTransaction(RequestTransaction request, Subscription subscription, Fund fund, User user) {
        return CANCELLATION.name().equals(request.getTransactionType()) ?
                Mono.just(updateSubscription(subscription))
                        .flatMap(subscriptionSave -> subscriptionRepository.save(subscriptionSave)
                                .map(subscriptionSaved -> updateUser(user, user.getAvailableBalance() + subscriptionSaved.getInitialValue()))
                                .flatMap(updateUser -> userRepository.save(updateUser))
                                .map(transaction -> createTransactionObject(subscription, request))
                                .flatMap(transaction -> transactionRepository.save(transaction)))
                        .map(transaction -> createResponse(transaction, SUBSCRIPTION_OPENING.getMessage())) :

                Mono.error(new ValidateArgumentsException(VALIDATE_ARGUMENTS.getMessage()));
    }

    private Mono<Response> handleOpeningTransaction(RequestTransaction request, Fund fund, User user) {
        return Mono.just(fund)
                .filter(fundOpening -> Objects.nonNull(request.getInitialValue()))
                .filter(fundOpening -> request.getInitialValue() >= fundOpening.getMinInitialValue())
                .switchIfEmpty(Mono.error(new MinValueException(MIN_VALUE.getMessage().concat(fund.getName()))))
                .filter(fundOpening -> request.getInitialValue() <= user.getAvailableBalance())
                .switchIfEmpty(Mono.error(new InsufficientFundException(INSUFFICIENT_FUND.getMessage())))
                .map(subscriptionSaved -> updateUser(user, user.getAvailableBalance() - request.getInitialValue()))
                .flatMap(updateUser -> userRepository.save(updateUser))
                .map(fundSaved -> createSubscription(request))
                .flatMap(subscriptionSave -> subscriptionRepository.save(subscriptionSave)
                        .flatMap(subs -> emailService.sendEmail("amvelasquez9621@gmail.com","Suscripcion exitosa","Se ha suscrito al fondo"))
                        .thenReturn(subscriptionSave))
                .map(subscriptionSaved -> createTransactionObject(subscriptionSaved, request))
                .flatMap(transaction -> transactionRepository.save(transaction))
                .map(transaction -> createResponse(transaction, SUBSCRIPTION_OPENING.getMessage()));
    }

    private Mono<Subscription> handleSubscription(RequestTransaction request) {
        if (OPENING.name().equals(request.getTransactionType())) {
            return subscriptionRepository.findByUserIdAndFundIdAndStatus(request.getUserId(), request.getFundId(), ACTIVE.name())
                    .doOnNext(subscription -> logger.info("Status: {}", subscription.getStatus()))
                    .flatMap(subscription -> {
                        if (ACTIVE.name().equals(subscription.getStatus())) {
                            return Mono.error(new SubscriptionException(ACTIVE_SUBSCRIPTION.getMessage()));
                        } else {
                            logger.info(SUBSCRIPTION_NOT_FOUND.getMessage());
                            return Mono.just(new Subscription());
                        }
                    }).switchIfEmpty(Mono.just(new Subscription()));
        } else if (CANCELLATION.name().equals(request.getTransactionType())) {
            if (Objects.nonNull(request.getSubscriptionId())) {
                return subscriptionRepository.findByIdAndUserId(request.getSubscriptionId(), request.getUserId())
                        .filter(subscription -> ACTIVE.name().equals(subscription.getStatus()))
                        .switchIfEmpty(Mono.error(new SubscriptionException(INACTIVE_SUBSCRIPTION.getMessage())));
            } else {
                return Mono.error(new SubscriptionException(VALIDATE_SUBSCRIPTION.getMessage()));
            }
        } else {
            logger.info(SUBSCRIPTION_NOT_FOUND.getMessage());
            return Mono.just(new Subscription());
        }
    }

    private Mono<Response> handleError(Throwable error) {
        return Mono.<Response>error(error)
                .onErrorResume(UserNotFoundException.class, e -> Mono.just(new Response(NOT_FOUND.value(), e.getMessage())))
                .onErrorResume(FundNotFoundException.class, e -> Mono.just(new Response(NOT_FOUND.value(), e.getMessage())))
                .onErrorResume(MinValueException.class, e -> Mono.just(new Response(NOT_FOUND.value(), e.getMessage())))
                .onErrorResume(SubscriptionException.class, e -> Mono.just(new Response(NOT_FOUND.value(), e.getMessage())))
                .onErrorResume(ValidateArgumentsException.class, e -> Mono.just(new Response(NOT_FOUND.value(), e.getMessage())))
                .onErrorResume(e -> {
                    logger.error(UNEXPECTED.getMessage(), e);
                    return Mono.just(new Response(NOT_FOUND.value(), e.getMessage()));
                });
    }

    @Override
    public Flux<Transaction> findAllByUserId(String userId) {
        return transactionRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new TransactionException(ALL_TRANSACTION.getMessage())));
    }
}
