package com.sasha.fitapp.repository;

import com.sasha.fitapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByTitle(String title);

    @Query("SELECT t FROM Task t WHERE year(t.created) = ?1 and month(t.created) = ?2 and day(t.created) = ?3")
    List<Task> findTaskByCreatedTime(int year, int monthValue, int dayOfMonth);

}