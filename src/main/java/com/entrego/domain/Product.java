package com.entrego.domain;

import java.time.LocalDateTime;

import com.entrego.dtos.ProductDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity(name = "products")
@Table(name = "products")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String name;
	private Double price;
	private int discount;
	@ManyToOne
	@JsonIgnore
	private Store store;
	@JsonIgnore
	private LocalDateTime createdAt;
	@JsonIgnore
	private LocalDateTime updatedAt;
	@ManyToOne
	@JsonIgnore
	private ProductCategory productCategory;

	public Product(ProductDTO data) {
		this.name = data.name();
		this.price = data.price();
		this.discount = data.discount();
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
	
	
}
