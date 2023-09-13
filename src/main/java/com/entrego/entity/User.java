package com.entrego.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.entrego.dtos.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	private String firstName;
	private String lastName;
	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private String cell;
	@Column(unique = true)
	private String document;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	@OneToMany
	private List<Address> address;
	@OneToMany
	private List<Request> requests;
	
	
	
	public User() {
		super();
	}

	public User(UserDTO data) {
		this.firstName = data.firstName();
		this.lastName = data.lastName();
		this.email = data.email();
		this.cell = data.cell();
		this.document = data.document();
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
		this.requests = data.requests();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}
	
	public void addAddress(Address address) {
		this.address.add(address);
	}

	public List<Request> getRequests() {
		return requests;
	}

	public void setRequests(List<Request> requests) {
		this.requests = requests;
	}

	public String getId() {
		return id;
	}
	
	
	
	
}
