package com.entrego.dtos;

public record AddressDTO(String cep, String number, String street, String complement, String neighborhood, String city, String country, boolean isMain) {

}
