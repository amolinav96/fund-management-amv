package com.co.fundmanagement.service.impl;

import com.co.fundmanagement.exception.FundNotFoundException;
import com.co.fundmanagement.model.Fund;
import com.co.fundmanagement.model.Response;
import com.co.fundmanagement.repository.FundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FundServiceTest {

    @Mock
    private FundRepository fundRepository;

    @InjectMocks
    private FundService fundService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        when(fundRepository.findAll()).thenReturn(Flux.just(builderFund(), builderFundTwo()));

        Flux<Fund> result = fundService.findAll();

        StepVerifier.create(result)
                .expectNextMatches(fund -> fund.getDescription().equals("description") && fund.getName().equals("fund1"))
                .expectNextMatches(fund -> fund.getDescription().equals("description2") && fund.getName().equals("fund2"))
                .verifyComplete();
    }

    @Test
    public void testFindByIdFound() {
        String fundId = "2541";
        Fund mockFund = builderFund();

        when(fundRepository.findById(fundId)).thenReturn(Mono.just(mockFund));

        Mono<Fund> result = fundService.findById(fundId);
        StepVerifier.create(result)
                .expectNextMatches(fund -> fund.getId().equals(fundId) && fund.getName().equals("fund1"))
                .verifyComplete();
    }

    @Test
    public void testFindByIdNotFound() {
        String fundId = "2";

        when(fundRepository.findById(fundId)).thenReturn(Mono.empty());

        Mono<Fund> result = fundService.findById(fundId);
        StepVerifier.create(result)
                .expectError(FundNotFoundException.class)
                .verify();
    }

    @Test
    public void testCreateFund() {
        Fund requestFund = Fund.builder()
                .name("new fund")
                .description("Description")
                .build();
        Fund savedFund = Fund.builder()
                .id("1")
                .name("new fund")
                .description("Description")
                .minInitialValue(1000.0)
                .build();

        when(fundRepository.save(any(Fund.class))).thenReturn(Mono.just(savedFund));

        Mono<Response> result = fundService.createFund(requestFund);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getCode().equals(200))
                .verifyComplete();
    }

    @Test
    public void testUpdateFund() {
        String id = "1";
        Fund requestFund = Fund.builder()
                .id("1")
                .name("new fund")
                .description("Description")
                .minInitialValue(100.0)
                .build();
        Fund savedFund = Fund.builder()
                .id("1")
                .name("new fund")
                .description("Description")
                .minInitialValue(100.0)
                .build();
        when(fundRepository.findById(id)).thenReturn(Mono.just(savedFund));
        when(fundRepository.save(any(Fund.class))).thenReturn(Mono.just(savedFund));

        Mono<Response> result = fundService.createFund(requestFund);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getCode().equals(200))
                .verifyComplete();
    }

    public Fund builderFund(){
        return Fund.builder()
                .id("2541")
                .name("fund1")
                .description("description")
                .minInitialValue(10.0)
                .build();
    }

    public Fund builderFundTwo(){
        return Fund.builder()
                .id(UUID.randomUUID().toString())
                .name("fund2")
                .description("description2")
                .minInitialValue(20.0)
                .build();
    }
}
