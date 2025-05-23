package com.entrego.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.entrego.dtos.RegisterUserRequestDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import org.hibernate.annotations.UpdateTimestamp;

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
	@JsonIgnore
	private String password;
	@Column(unique = true)
	private String cell;
	@Column(unique = true)
	private String document;
	@JsonIgnore
	private LocalDateTime createdAt;
	@JsonIgnore
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	@OneToMany
	@JsonIgnore
	private List<Order> orders;

	public User(RegisterUserRequestDTO data) {
		this.firstName = data.firstName();
		this.lastName = data.lastName();
		this.email = data.email();
		this.cell = data.cell();
		this.document = data.document();
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
		this.orders = data.orders();
	}


}
