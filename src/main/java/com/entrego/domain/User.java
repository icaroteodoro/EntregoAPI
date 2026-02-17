package com.entrego.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.entrego.dtos.user.request.RegisterUserRequestDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
	@OneToOne
	@JoinColumn(name = "account_id")
	private Account account;

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

	public User(RegisterUserRequestDTO data, Account account) {
		this.firstName = data.firstName();
		this.lastName = data.lastName();
		this.account = account;
		this.cell = data.cell();
		this.document = data.document();
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
		this.orders = data.orders();
	}


}
