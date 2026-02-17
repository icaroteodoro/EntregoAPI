package com.entrego.dtos.store.response;

import java.time.LocalDateTime;
import com.entrego.domain.Store;

import com.entrego.dtos.address.AddressDTO; // Need to check import for AddressDTO.
import com.entrego.enums.StoreCategoryEnum;
import com.entrego.enums.StoreStatus;

public record StoreResponseDTO(String id, String name, String document, String email, String role,
                               String description, AddressDTO address,
                               String urlProfileImage, String urlCoverImage,
                               LocalDateTime createdAt, LocalDateTime updateAt, StoreCategoryEnum category, StoreStatus statusLive) {

    public StoreResponseDTO(Store store) {
        this(store.getId(), store.getName(), store.getDocument(), store.getAccount().getEmail(), store.getAccount().getRole(),
             store.getDescription(), new AddressDTO(store.getAddress().getCep(), store.getAddress().getNumber(), store.getAddress().getStreet(), store.getAddress().getComplement(), store.getAddress().getNeighborhood(), store.getAddress().getCity(), store.getAddress().getCountry(), store.getAddress().isMain()), store.getUrlProfileImage(), store.getUrlCoverImage(),
             store.getCreatedAt(), store.getUpdatedAt(), store.getCategory(), store.getStatusLive());
    }
}
