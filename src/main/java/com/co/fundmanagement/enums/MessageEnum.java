package com.co.fundmanagement.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MessageEnum {
    SUBSCRIPTION_OPENING("Subscripcion iniciada con exito"),
    SUBSCRIPTION_CANCELLED("Subscripcion cancelada con exito - El valor inicial de la vinculacion fue devuelto"),
    CREATE_FUND("Result create fund");
    private String message;
}
