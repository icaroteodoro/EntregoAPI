package com.entrego.repositories;

import com.entrego.domain.ItemOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOrderRepository extends JpaRepository<ItemOrder, String> {
}
