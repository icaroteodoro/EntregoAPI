package com.entrego.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.entrego.dtos.RegisterStoreRequestDTO;

import com.entrego.enums.StoreCategoryEnum;
import com.entrego.enums.StoreStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "stores")
@Table(name = "stores")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Store {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String name;
	@Column(unique = true)
	private String document;
	@Column(unique = true)
	private String email;
	private String password;
	private String description;
	private String urlProfileImage;
	private String urlCoverImage;
	private StoreCategoryEnum category;
	private StoreStatus statusLive;
	@OneToOne
	@JsonIgnore
	private Address address;
	@OneToMany
	@JsonIgnore
	private List<Order> orders;
	@OneToMany
	@JsonIgnore
	private List<Product> products;
	@OneToMany
	@JsonIgnore
	private List<ProductCategory> categories;

	@JsonIgnore
	private LocalDateTime createdAt;
	@JsonIgnore
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public Store(RegisterStoreRequestDTO data) {
		this.name = data.name();
		this.document = data.document();
		this.email = data.email();
		this.description = data.description();
		this.address = new Address(data.address());
		this.statusLive = StoreStatus.fromValue("CLOSED");
		this.orders = data.orders();
		this.category = data.category();
		this.products = data.products();
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

}
