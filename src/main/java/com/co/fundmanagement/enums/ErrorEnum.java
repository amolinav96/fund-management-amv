package com.co.fundmanagement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorEnum {
    USER_NOT_FOUND("User not found"),
    FUND_NOT_FOUND("Fund not found"),
    SUBSCRIPTION_NOT_FOUND("No subscription found, creating a new one"),
    ACTIVE_SUBSCRIPTION("Ya tienes una subscripcion activa con este fondo"),
    INACTIVE_SUBSCRIPTION("No existe la subscripcion, se encuentra inactiva o no es de su propiedad"),
    MIN_VALUE("No tiene saldo disponible para vincularse al fondo "),
    VALIDATE_ARGUMENTS("Validate subscriptionId - TransactionType not found"),
    UNEXPECTED("Unexpected error"),
    VALIDATE_SUBSCRIPTION("Debes ingresar la subscripcion que deseas cancelar"),
    ALL_TRANSACTION("The user has no transaction");

    private String message;
}
