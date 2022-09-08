package com.sasha.fitapp.service;

import com.sasha.fitapp.model.Task;
import com.sasha.fitapp.repository.TaskRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(Task task){
        logger.info("Save {}", task);
        return taskRepository.save(task);
    }

    public List<Task> addTasks(List<Task> tasks) {
        logger.info("Save {}", tasks);
        return taskRepository.saveAll(tasks);
    }

    public List<Task> getTaskByCreatedTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        return taskRepository.findTaskByCreatedTime(localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth());
    }
}
