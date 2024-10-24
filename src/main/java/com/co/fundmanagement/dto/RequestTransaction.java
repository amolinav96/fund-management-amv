package com.co.fundmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestTransaction {
    private String userId;
    private String fundId;
    private String transactionType;
    private String subscriptionId;
    private Double initialValue;

}
