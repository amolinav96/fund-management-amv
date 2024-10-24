package com.co.fundmanagement.repository;

import com.co.fundmanagement.model.Fund;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FundRepository extends ReactiveMongoRepository<Fund, String> {
}
