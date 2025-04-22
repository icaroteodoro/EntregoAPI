package com.entrego.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "items")
@Table(name = "items")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class ItemOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private Double price;

    private int quantity;


}
