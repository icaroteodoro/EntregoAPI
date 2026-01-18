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

	List<Order> findOrdersByStoreAccountEmailOrderByCreatedAt(String email);


	@Query("SELECT o FROM orders o WHERE o.store.account.email = :email AND DAY(o.createdAt) = :dayOfMonth")
	List<Order> findOrdersByStoreAccountEmailAndCreatedAt_DayOfMonthOrderByCreatedAt(@Param("email") String email, @Param("dayOfMonth") int dayOfMonth);



}
