package com.sasha.fitapp.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String title;

    @Column(length = 200)
    private String content;

    @Column
    @CreatedDate
    private LocalDateTime created;

    @Column
    private boolean isDone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_tasks",
            joinColumns = { @JoinColumn(name = "task_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }
    )
    private User user;
}
