package com.entrego.dtos.store.request;

import com.entrego.enums.StoreStatus;

public record RequestUpdateStoreStatus(String email, String status) {
}
