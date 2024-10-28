package com.co.fundmanagement.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FundTest {

    @Test
    public void testFundCreationUsingBuilder() {
        String id = "1";
        String name = "Test Fund";
        String description = "A fund for testing";
        Double minInitialValue = 1000.0;

        Fund fund = Fund.builder()
                .id(id)
                .name(name)
                .description(description)
                .minInitialValue(minInitialValue)
                .build();

        assertEquals(id, fund.getId());
        assertEquals(name, fund.getName());
        assertEquals(description, fund.getDescription());
        assertEquals(minInitialValue, fund.getMinInitialValue());
    }

    @Test
    public void testFundNoArgsConstructor() {
        Fund fund = new Fund();

        assertNull(fund.getId());
        assertNull(fund.getName());
        assertNull(fund.getDescription());
        assertNull(fund.getMinInitialValue());
    }

    @Test
    public void testFundAllArgsConstructor() {
        String id = "2";
        String name = "Another Fund";
        String description = "Another fund for testing";
        Double minInitialValue = 5000.0;

        Fund fund = new Fund(id, name, description, minInitialValue);

        assertEquals(id, fund.getId());
        assertEquals(name, fund.getName());
        assertEquals(description, fund.getDescription());
        assertEquals(minInitialValue, fund.getMinInitialValue());
    }

}
