package com.entrego.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.entrego.dtos.EnterpriseDTO;

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

@Entity(name = "enterprises")
@Table(name = "enterprises")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Enterprise {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String name;
	@Column(unique = true)
	private String document;
	@Column(unique = true)
	private String email;
	private String description;
	@OneToOne
	@JsonIgnore
	private Address address;
	@OneToMany
	@JsonIgnore
	private List<Request> requests;
	@OneToMany
	@JsonIgnore
	private List<Product> products;

	@JsonIgnore
	private LocalDateTime createdAt;
	@JsonIgnore
	private LocalDateTime updatedAt;

	public Enterprise(EnterpriseDTO data) {
		this.name = data.name();
		this.document = data.document();
		this.email = data.email();
		this.description = data.description();
		Address newAddress = new Address(data.address());
		this.address = newAddress;
		this.requests = data.requests();
		this.products = data.products();
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
	
}
