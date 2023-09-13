package com.entrego.dtos;

import com.entrego.entity.User;

public record AddressDTO(String cep, String number, String street, String neighborhood, String city, String country, boolean isMain, User user) {

}
