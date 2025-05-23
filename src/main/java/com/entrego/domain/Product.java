package com.entrego.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.entrego.dtos.ProductDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.type.Decimal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

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
	private BigDecimal price;
	private String urlImage;
	private int discount;
	@ManyToOne
	@JsonIgnore
	private Store store;
	@JsonIgnore
	private LocalDateTime createdAt;
	@JsonIgnore
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	@ManyToOne
	private ProductCategory productCategory;

	public Product(ProductDTO data) {
		this.name = data.name();
		this.price = data.price();
		this.discount = data.discount();
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
	
	
}
