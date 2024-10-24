package com.co.fundmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Transaction {
    @Id
    private String id;
    private Date date;
    private String userId;
    private String subscriptionId;
    private String transactionType;
}
