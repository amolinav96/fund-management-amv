package com.co.fundmanagement.factory;

import com.co.fundmanagement.dto.RequestTransaction;
import com.co.fundmanagement.enums.SubscriptionStatusEnum;
import com.co.fundmanagement.model.Fund;
import com.co.fundmanagement.model.Response;
import com.co.fundmanagement.model.Subscription;
import com.co.fundmanagement.model.Transaction;

import java.util.Date;

public class FactoryObject {

    public static Fund createFund(Fund fund, Double balance){
        return Fund.builder()
                .id(fund.getId())
                .name(fund.getName())
                .description(fund.getDescription())
                .minInitialValue(fund.getMinInitialValue())
                .balance(balance)
                .build();
    }

    public static Subscription createSubscription(RequestTransaction request){
        return Subscription.builder()
                .userId(request.getUserId())
                .fundId(request.getFundId())
                .status(SubscriptionStatusEnum.ACTIVE.name())
                .openingDate(new Date())
                .initialValue(request.getInitialValue())
                .build();
    }

    public static Subscription updateSubscription(Subscription subscription){
        return Subscription.builder()
                .id(subscription.getId())
                .userId(subscription.getUserId())
                .fundId(subscription.getFundId())
                .initialValue(subscription.getInitialValue())
                .openingDate(subscription.getOpeningDate())
                .cancellationDate(new Date())
                .status(SubscriptionStatusEnum.INACTIVE.name())
                .build();
    }

    public static Transaction createTransactionObject(Subscription subscription, RequestTransaction request){
        return Transaction.builder()
                .date(new Date())
                .subscriptionId(subscription.getId())
                .userId(request.getUserId())
                .transactionType(request.getTransactionType())
                .build();
    }

    public static Response createResponse(Object object, String message){
        return Response.builder()
                .code(200)
                .message(message)
                .body(object)
                .build();
    }
}
