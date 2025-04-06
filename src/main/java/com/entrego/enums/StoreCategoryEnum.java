package com.entrego.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StoreCategoryEnum {
    LANCHES("LANCHES"),
    PIZZA("PIZZA"),
    JAPONESA("JAPONESA"),
    ACAI("ACAI"),
    DOCES("DOCES"),
    SALGADOS("SALGADOS"),
    MARMITA("MARMITA"),
    BRASILEIRA("BRASILEIRA"),
    SORVETE("SORVETE"),
    ITALIANA("ITALIANA"),
    PADARIA("PADARIA"),
    CHINESA("CHINESA"),
    GOURMET("GOURMET");




    private final String value;

    StoreCategoryEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static StoreCategoryEnum fromValue(String value) {
        for (StoreCategoryEnum category : values()) {
            if (category.value.equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Categoria inválida: " + value);
    }

}
