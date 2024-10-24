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
    MESSAGE_SEND_EMAIL("Se ha suscrito al fondo de manera exitosa"),
    SUBJECT("Mensaje desde fund management amv"),
    CREATE_FUND("Result create fund");
    private String message;
}
