package com.entrego.dtos.store.request;

import com.entrego.enums.StoreCategoryEnum;
import com.entrego.enums.StoreStatus;

public record RequestUpdateStore(String name, String description, StoreCategoryEnum category) {
}
