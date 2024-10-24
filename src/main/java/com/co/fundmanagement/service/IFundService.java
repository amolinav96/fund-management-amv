package com.co.fundmanagement.service;

import com.co.fundmanagement.model.Fund;
import com.co.fundmanagement.model.Response;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IFundService {

    Flux<Fund> findAll();
    Mono<Fund> findById(String id);
    Mono<Response> createFund(Fund fund);
}
