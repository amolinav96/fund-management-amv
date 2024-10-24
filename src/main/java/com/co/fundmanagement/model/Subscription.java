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
@Document(collection = "subscription")
public class Subscription {
    @Id
    private String id;
    private String userId;
    private String fundId;
    private String status;
    private Date openingDate;
    private Date cancellationDate;
    private Double initialValue;
}
