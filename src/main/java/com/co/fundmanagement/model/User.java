package com.co.fundmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class User {
    @Id
    private String id;
    private String fullName;
    private String identificationNumber;
    private String identificationType;
    private Double availableBalance;
    private String email;
}
