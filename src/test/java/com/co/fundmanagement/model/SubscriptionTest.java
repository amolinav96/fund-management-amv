package com.co.fundmanagement.model;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SubscriptionTest {

    @Test
    public void testSubscriptionBuilder() {
        // Given
        String id = "1";
        String userId = "user123";
        String fundId = "fund456";
        String status = "active";
        Date openingDate = new Date();
        Double initialValue = 1000.0;

        // When
        Subscription subscription = Subscription.builder()
                .id(id)
                .userId(userId)
                .fundId(fundId)
                .status(status)
                .openingDate(openingDate)
                .initialValue(initialValue)
                .build();

        // Then
        assertEquals(id, subscription.getId());
        assertEquals(userId, subscription.getUserId());
        assertEquals(fundId, subscription.getFundId());
        assertEquals(status, subscription.getStatus());
        assertEquals(openingDate, subscription.getOpeningDate());
        assertEquals(initialValue, subscription.getInitialValue());
        assertNull(subscription.getCancellationDate()); // Asegúrate de que sea null por defecto
    }

    @Test
    public void testSubscriptionNoArgsConstructor() {
        // When
        Subscription subscription = new Subscription();

        // Then
        assertNull(subscription.getId());
        assertNull(subscription.getUserId());
        assertNull(subscription.getFundId());
        assertNull(subscription.getStatus());
        assertNull(subscription.getOpeningDate());
        assertNull(subscription.getCancellationDate());
        assertNull(subscription.getInitialValue());
    }

    @Test
    public void testSubscriptionAllArgsConstructor() {
        // Given
        String id = "2";
        String userId = "user789";
        String fundId = "fund012";
        String status = "inactive";
        Date openingDate = new Date();
        Date cancellationDate = new Date();
        Double initialValue = 5000.0;

        // When
        Subscription subscription = new Subscription(id, userId, fundId, status, openingDate, cancellationDate, initialValue);

        // Then
        assertEquals(id, subscription.getId());
        assertEquals(userId, subscription.getUserId());
        assertEquals(fundId, subscription.getFundId());
        assertEquals(status, subscription.getStatus());
        assertEquals(openingDate, subscription.getOpeningDate());
        assertEquals(cancellationDate, subscription.getCancellationDate());
        assertEquals(initialValue, subscription.getInitialValue());
    }
}
