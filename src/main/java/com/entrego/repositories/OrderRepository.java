package com.entrego.repositories;



import java.time.LocalDateTime;
import java.util.List;

import com.entrego.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, String> {
	List<Order> findOrdersByUserId(String id);
	List<Order> findOrdersByStoreId(String id);

	List<Order> findOrdersByStoreEmail(String email);

	@Query("SELECT o FROM orders o WHERE o.store.email = :email AND DAY(o.createdAt) = :dayOfMonth")
	List<Order> findOrdersByStoreEmailAndCreatedAt_DayOfMonth(@Param("email") String email, @Param("dayOfMonth") int dayOfMonth);


}
