package com.entrego.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name = "product_options")
@Table(name = "product_options")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    
    private String description;

    private BigDecimal price; // Additional price (can be 0)
    
    private Boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnore
    private ProductOptionGroup group;
}
