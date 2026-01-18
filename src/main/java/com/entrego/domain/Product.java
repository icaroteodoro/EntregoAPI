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
	private BigDecimal minPrice;
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

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private java.util.List<ProductOptionGroup> optionGroups;

	public Product(ProductDTO data) {
		this.name = data.name();
		this.price = data.price();
		this.minPrice = data.minPrice();
		this.discount = data.discount();
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();

		if (data.optionGroups() != null) {
			this.optionGroups = data.optionGroups().stream().map(groupDTO -> {
				ProductOptionGroup group = new ProductOptionGroup();
				group.setName(groupDTO.name());
				group.setMinSelection(groupDTO.minSelection());
				group.setMaxSelection(groupDTO.maxSelection());
				group.setProduct(this);

				if (groupDTO.options() != null) {
					group.setOptions(groupDTO.options().stream().map(optionDTO -> {
						ProductOption option = new ProductOption();
						option.setName(optionDTO.name());
						option.setDescription(optionDTO.description());
						option.setPrice(optionDTO.price());
						option.setIsAvailable(optionDTO.isAvailable());
						option.setGroup(group);
						return option;
					}).collect(java.util.stream.Collectors.toList()));
				}
				return group;
			}).collect(java.util.stream.Collectors.toList());
		}
	}
	
	
}
