package com.sasha.fitapp.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private BigDecimal amount;

    private UnitOfMeasurement unitOfMeasurement;

    @ManyToOne
    @ToString.Exclude
    private Recipe recipe;
}
