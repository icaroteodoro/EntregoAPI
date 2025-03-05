package com.entrego.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.entrego.dtos.OrderDTO;

import com.entrego.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




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
	private double total;
	@ManyToOne
	@JsonIgnore
	private User user;
	@ManyToOne
	@JsonIgnore
	private Store store;
	@ManyToMany
	@JsonIgnore
	private List<Product> products;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Order(OrderDTO data) {
		this.status = OrderStatus.MADE;
		this.products = data.products();
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

}
