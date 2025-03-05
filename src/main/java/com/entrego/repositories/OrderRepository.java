package com.entrego.repositories;



import java.util.List;

import com.entrego.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
	List<Order> findOrdersByUserId(String id);
	List<Order> findOrdersByStoreId(String id);
}
