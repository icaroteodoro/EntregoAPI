package com.entrego.entity;

import com.entrego.dtos.AddressDTO;

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


	public Address() {
		super();
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


	public String getCep() {
		return cep;
	}


	public void setCep(String cep) {
		this.cep = cep;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getNeighborhood() {
		return neighborhood;
	}


	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public boolean isMain() {
		return isMain;
	}


	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}	
	
	
	
}
