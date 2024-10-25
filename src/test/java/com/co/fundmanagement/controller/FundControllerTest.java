package com.co.fundmanagement.controller;

import com.co.fundmanagement.model.Fund;
import com.co.fundmanagement.model.Response;
import com.co.fundmanagement.service.impl.FundService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebFluxTest(FundController.class)
class FundControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private FundService fundService;

    @Test
    void testFindAllFunds() {
        Fund fund1 = new Fund("1", "Fund 1", "Description 1", 1000.0);
        Fund fund2 = new Fund("2", "Fund 2", "Description 2", 2000.0);

        when(fundService.findAll()).thenReturn(Flux.just(fund1, fund2));

        webTestClient.get()
                .uri("/fund/all")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testFindFundById() {
        String fundId = "1";
        Fund fund = new Fund(fundId, "Fund 1", "Description 1", 1000.0);

        when(fundService.findById(fundId)).thenReturn(Mono.just(fund));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/fund/byId").queryParam("id", fundId).build())
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testCreateFund() {
        Fund requestFund = new Fund(null, "New Fund", "New Fund Description", 1000.0);
        Response response = new Response(201, "Fund created successfully");

        when(fundService.createFund(any(Fund.class))).thenReturn(Mono.just(response));

        webTestClient.post()
                .uri("/fund/create")
                .contentType(APPLICATION_JSON)
                .bodyValue(requestFund)
                .exchange()
                .expectStatus().isOk();
    }
}
