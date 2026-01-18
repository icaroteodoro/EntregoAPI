package com.entrego.domain;

import com.google.type.Decimal;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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

    private BigDecimal price;

    private int quantity;

    @OneToMany(mappedBy = "itemOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<ItemOrderOption> options;


}
