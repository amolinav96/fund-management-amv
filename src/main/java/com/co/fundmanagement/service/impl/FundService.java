package com.co.fundmanagement.service.impl;

import com.co.fundmanagement.exception.FundNotFoundException;
import com.co.fundmanagement.model.Fund;
import com.co.fundmanagement.model.Response;
import com.co.fundmanagement.repository.FundRepository;
import com.co.fundmanagement.service.IFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static com.co.fundmanagement.enums.ErrorEnum.FUND_NOT_FOUND;
import static com.co.fundmanagement.enums.MessageEnum.CREATE_FUND;
import static com.co.fundmanagement.factory.FactoryObject.createResponse;

@Service
public class FundService implements IFundService {
    @Autowired
    private FundRepository fundRepository;

    @Override
    public Flux<Fund> findAll() {
        return fundRepository.findAll();
    }

    @Override
    public Mono<Fund> findById(String id) {
        return fundRepository.findById(id)
                .switchIfEmpty(Mono.error(new FundNotFoundException(FUND_NOT_FOUND.getMessage())));
    }

    @Override
    public Mono<Response> createFund(Fund request) {
        return Mono.just(request)
                .filter(requestFund -> Objects.nonNull(request.getId()))
                .flatMap(requestFund -> fundRepository.findById(request.getId()))
                .map(requestFund -> Fund.builder()
                        .id(requestFund.getId())
                        .name(request.getName())
                        .description(request.getDescription())
                        .minInitialValue(request.getMinInitialValue())
                        .balance(requestFund.getBalance())
                        .build())
                .switchIfEmpty(Mono.just(Fund.builder()
                        .name(request.getName())
                        .description(request.getDescription())
                        .minInitialValue(request.getMinInitialValue())
                        .balance(0.0)
                        .build()))
                .flatMap(fund -> fundRepository.save(fund))
                .map(fund -> createResponse(fund, CREATE_FUND.getMessage()));
    }

}
