package com.entrego.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
    MADE("MADE"),
    ACCEPTED("ACCEPTED"),
    DOING("DOING"),
    SENT("SENT"),
    FINISHED("FINISHED");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static OrderStatus fromValue(String value) {
        for (OrderStatus status : values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + value);
    }

}
