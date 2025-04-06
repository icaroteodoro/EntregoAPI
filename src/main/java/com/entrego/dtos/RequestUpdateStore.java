package com.entrego.dtos;

import com.entrego.enums.StoreCategoryEnum;
import com.entrego.enums.StoreStatus;

public record RequestUpdateStore(String name, String description, StoreCategoryEnum category) {
}
