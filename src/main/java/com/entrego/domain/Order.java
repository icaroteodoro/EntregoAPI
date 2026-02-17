package com.entrego.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.entrego.dtos.order.ItemsOrderResponse;
import com.entrego.dtos.order.OrderDTO;

import com.entrego.dtos.order.OrderResponse;
import com.entrego.enums.OrderStatus;
import com.entrego.enums.PaymentMethod;
import com.entrego.repositories.AddressRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;


@Entity(name = "orders")
@Table(name = "orders")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	private String numberOrder;
	private BigDecimal total;
	@ManyToOne
	private User user;
	@ManyToOne
	private Store store;
	@ManyToMany
	private List<ItemOrder> items;

	private PaymentMethod paymentMethod;
	@Column(nullable = false)
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public Order(OrderDTO data) {
		this.status = OrderStatus.MADE;
		this.numberOrder = randomNumber();
		this.paymentMethod = data.paymentMethod();
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public static String randomNumber() {
		Random random = new Random();
		int randomNumber = random.nextInt(9000) + 1000;
		return String.valueOf(randomNumber);
	}


	@Embedded
	private OrderAddress address;

	public OrderResponse getOrderResponse() {
        return new OrderResponse(
				this.getId(),
				this.getUser().getFirstName() + " " + this.getUser().getLastName(),
				this.getNumberOrder(),
				this.getTotal(),
				this.getCreatedAt(),
				this.getStatus(),
				this.getAddress(),
				this.getPaymentMethod(),
				this.getItems()
		);
	}

}
