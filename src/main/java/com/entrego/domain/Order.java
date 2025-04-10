package com.entrego.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.entrego.dtos.ItemsOrderResponse;
import com.entrego.dtos.OrderDTO;

import com.entrego.dtos.OrderResponse;
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
	private double total;
	@ManyToOne
	private User user;
	@ManyToOne
	private Store store;
	@ManyToMany
	private List<Product> products;

	private PaymentMethod paymentMethod;
	@Column(nullable = false)
	private LocalDateTime createdAt;
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

	public List<ItemsOrderResponse> getListItems() {
		List<Product> products = this.getProducts();
		List<ItemsOrderResponse> items = new ArrayList<>();

		Map<Product, Integer> productCount = new HashMap<>();

		for (Product product : products) {
			productCount.put(product, productCount.getOrDefault(product, 0) + 1);
		}

		for (Map.Entry<Product, Integer> entry : productCount.entrySet()) {
			Product product = entry.getKey();
			int quantity = entry.getValue();
			double totalPrice = product.getPrice() * quantity;

			items.add(new ItemsOrderResponse(
					product.getName(),
					totalPrice,
					quantity
			));
		}
		return items;
	}

	public OrderResponse getOrderResponse(AddressRepository addressRepository) {
        return new OrderResponse(
				this.getId(),
				this.getUser().getFirstName() + " " + this.getUser().getLastName(),
				this.getNumberOrder(),
				this.getCreatedAt(),
				this.getStatus(),
				addressRepository.findAddressByUserIdAndIsMain(this.getUser().getId()),
				this.getPaymentMethod(),
				this.getListItems()
		);
	}

}
