package com.entrego.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "item_order_options")
@Table(name = "item_order_options")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItemOrderOption {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "item_order_id")
    @JsonIgnore
    private ItemOrder itemOrder;

    public ItemOrderOption(String name, BigDecimal price, ItemOrder itemOrder) {
        this.name = name;
        this.price = price;
        this.itemOrder = itemOrder;
    }
}
