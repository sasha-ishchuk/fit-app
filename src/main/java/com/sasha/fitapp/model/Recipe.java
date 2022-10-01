package com.sasha.fitapp.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String imageUrl;

    private Integer prepareTime;

    private Integer cookTime;

    private Integer servings;

    @Column(length = 8000)
    private String cookProcess;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    @ToString.Exclude
    private Set<Ingredient> ingredients = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "recipe")
    @ToString.Exclude
    private NutritionValue nutritionValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_recipes",
            joinColumns = { @JoinColumn(name = "recipe_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }
    )
    @ToString.Exclude
    private User user;
}
