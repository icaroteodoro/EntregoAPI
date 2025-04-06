package com.entrego.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentMethod {
    PIX("PIX"),
    CREDITO("CREDITO"),
    DEBITO("DEBITO"),
    DINHEIRO("DINHEIRO");

    private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static PaymentMethod fromValue(String value) {
        for (PaymentMethod method : values()) {
            if (method.value.equalsIgnoreCase(value)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Método inválido inválido: " + value);
    }
}
