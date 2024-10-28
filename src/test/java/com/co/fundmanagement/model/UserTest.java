package com.co.fundmanagement.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserTest {

    @Test
    public void testUserBuilder() {
        String id = "1";
        String fullName = "John Doe";
        String identificationNumber = "123456789";
        String identificationType = "Passport";
        Double availableBalance = 1500.0;
        String email = "john.doe@example.com";

        User user = User.builder()
                .id(id)
                .fullName(fullName)
                .identificationNumber(identificationNumber)
                .identificationType(identificationType)
                .availableBalance(availableBalance)
                .email(email)
                .build();

        assertEquals(id, user.getId());
        assertEquals(fullName, user.getFullName());
        assertEquals(identificationNumber, user.getIdentificationNumber());
        assertEquals(identificationType, user.getIdentificationType());
        assertEquals(availableBalance, user.getAvailableBalance());
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testUserNoArgsConstructor() {
        User user = new User();

        assertNull(user.getId());
        assertNull(user.getFullName());
        assertNull(user.getIdentificationNumber());
        assertNull(user.getIdentificationType());
        assertNull(user.getAvailableBalance());
        assertNull(user.getEmail());
    }

    @Test
    public void testUserAllArgsConstructor() {
        String id = "2";
        String fullName = "Jane Doe";
        String identificationNumber = "987654321";
        String identificationType = "ID Card";
        Double availableBalance = 2000.0;
        String email = "jane.doe@example.com";

        User user = new User(id, fullName, identificationNumber, identificationType, availableBalance, email);

        assertEquals(id, user.getId());
        assertEquals(fullName, user.getFullName());
        assertEquals(identificationNumber, user.getIdentificationNumber());
        assertEquals(identificationType, user.getIdentificationType());
        assertEquals(availableBalance, user.getAvailableBalance());
        assertEquals(email, user.getEmail());
    }
}
