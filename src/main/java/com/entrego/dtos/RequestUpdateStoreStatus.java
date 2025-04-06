package com.entrego.dtos;

import com.entrego.enums.StoreStatus;

public record RequestUpdateStoreStatus(String email, String status) {
}
