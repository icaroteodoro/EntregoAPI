package com.entrego.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StoreStatus {
    CLOSED("CLOSED"),
    OPEN("OPEN"),
    CLOSING("CLOSING");

    private final String value;

    StoreStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static StoreStatus fromValue(String value) {
        for (StoreStatus status : values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + value);
    }
}
