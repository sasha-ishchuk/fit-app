package com.sasha.fitapp.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String firstName;

    @Column(nullable = false, length = 20)
    private String lastName;

    @Column(nullable = false, length = 45, unique = true)
    private String email;

    @Column(nullable = false, length = 64, unique = true)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") }
    )
    private List<Role> roles = new ArrayList<>();

//    @OneToMany
//    @JoinTable(
//            name = "user_recipes",
//            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
//            inverseJoinColumns = { @JoinColumn(name = "recipe_id", referencedColumnName = "id") }
//    )
//    private List<Recipe> recipes = new ArrayList<>();

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "user_tasks",
//            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
//            inverseJoinColumns = { @JoinColumn(name = "task_id", referencedColumnName = "id") }
//    )
//    private List<Task> tasks = new ArrayList<>();

}
