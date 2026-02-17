package com.entrego.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderAddress {
    private String cep;
    private String number;
    private String street;
    private String complement;
    private String neighborhood;
    private String city;
    private String country;

    public OrderAddress(Address address) {
        this.cep = address.getCep();
        this.number = address.getNumber();
        this.street = address.getStreet();
        this.complement = address.getComplement();
        this.neighborhood = address.getNeighborhood();
        this.city = address.getCity();
        this.country = address.getCountry();
    }
}
