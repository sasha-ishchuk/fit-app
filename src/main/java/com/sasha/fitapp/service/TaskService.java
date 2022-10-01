package com.sasha.fitapp.service;

import com.sasha.fitapp.model.Task;
import com.sasha.fitapp.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        logger.info("Saving {}", task);
        return taskRepository.save(task);
    }

    public List<Task> addTasks(List<Task> tasks) {
        logger.info("Saving {}", tasks);
        return taskRepository.saveAll(tasks);
    }

    public List<Task> getTaskByCreatedTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        return taskRepository.findTaskByCreatedTime(localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth());
    }

    public Task findById(long id){
        return taskRepository.findById(id).orElseThrow();
    }

    public Task updateTask(Task task){
        logger.info("Updating {}", task);
        return taskRepository.save(task);
    }

    public void deleteTasks(List<Task> tasks){
        logger.info("Deleting {}", tasks);
        taskRepository.deleteAll(tasks);
    }

    public void deleteTaskById(long id){
        logger.info("Deleting TASK with id={}", id);
        taskRepository.deleteById(id);
    }

}
