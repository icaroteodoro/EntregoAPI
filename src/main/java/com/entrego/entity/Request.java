package com.entrego.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.entrego.dtos.RequestDTO;

import com.entrego.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Entity(name = "requests")
@Table(name = "requests")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Request {
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
	private Enterprise enterprise;
	@ManyToMany
	@JsonIgnore
	private List<Product> products;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Request(RequestDTO data) {
		this.status = OrderStatus.MADE;
		this.products = data.products();
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

}
