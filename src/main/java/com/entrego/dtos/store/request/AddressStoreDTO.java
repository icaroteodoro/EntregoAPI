package com.entrego.dtos.store.request;

public record AddressStoreDTO(String cep, String number, String street, String complement, String neighborhood, String city, String country) {

}
