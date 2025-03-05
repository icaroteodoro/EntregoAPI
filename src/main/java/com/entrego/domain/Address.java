package com.entrego.domain;

import com.entrego.dtos.AddressDTO;

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


@Entity(name= "address")
@Table(name="address")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String cep;
	private String number;
	private String street;
	private String neighborhood;
	private String city;
	private String country;
	private boolean isMain;
	@ManyToOne
	@JsonIgnore
	private User user;
	
	public Address(AddressDTO data) {
		this.cep = data.cep();
		this.number = data.number();
		this.street = data.street();
		this.neighborhood = data.neighborhood();
		this.city = data.city();
		this.country = data.country();
		this.isMain = data.isMain();
	}

	public Address(Address address) {
		super();
		this.cep = address.getCep();
		this.number = address.getNumber();
		this.street = address.getStreet();
		this.neighborhood = address.getNeighborhood();
		this.city = address.getCity();
		this.country = address.getCountry();
		this.isMain = address.isMain();
		this.user = address.getUser();
	}

}
