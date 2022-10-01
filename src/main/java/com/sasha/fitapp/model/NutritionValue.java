package com.sasha.fitapp.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "nutrition_values")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class NutritionValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal calories;

    private BigDecimal proteins;

    private BigDecimal fats;

    private BigDecimal carbohydrates;

    @OneToOne
    @ToString.Exclude
    private Recipe recipe;

}
