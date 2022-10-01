package com.sasha.fitapp.controller;

import com.sasha.fitapp.model.Task;
import com.sasha.fitapp.model.User;
import com.sasha.fitapp.service.TaskService;
import com.sasha.fitapp.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final UserService userService;
    private final TaskService taskService;

    public TaskController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/user/{id}/todo-list")
    public String getToDoList(@PathVariable("id") long id, Model model){
        List<Task> tasksByCreatedTime = taskService.getTaskByCreatedTime();

        tasksByCreatedTime.removeIf(task -> task.getUser().getId() != id);

        model.addAttribute("tasks", tasksByCreatedTime);
        model.addAttribute("id", id);

        return "todo-list";
    }

    @PostMapping( "/user/{id}/todo-list/task/add")
    public String addTask(@PathVariable(name = "id") long id, Model model,
                                @RequestParam(name = "taskTitle", required = false) String userTaskTitle,
                                @RequestParam(name = "taskContent", required = false) String userTaskContent){

        List<Task> userTasks = new ArrayList<>();

        LocalDateTime localDateTime = LocalDateTime.now();
        User currentUser = userService.findUserById(id);

        if(StringUtils.isNotEmpty(userTaskTitle) && StringUtils.isNotEmpty(userTaskContent)){
            userTasks.add(getTask(userTaskTitle, userTaskContent, localDateTime, currentUser, false));
        }

        model.addAttribute("taskTitle", userTaskTitle);
        model.addAttribute("taskContent", userTaskContent);

        if(!userTasks.isEmpty()){
            logger.info("Saved {}", taskService.addTasks(userTasks));
        }
        model.addAttribute("tasks", userTasks);

        return "redirect:/user/" + id + "/todo-list";
    }

    @GetMapping("/user/{id}/task/{taskId}/edit")
    public String getEditTaskPage(@PathVariable("id") long id,
                                  @PathVariable("taskId") long taskId,
                                  Model model) {

        Task existingTask = taskService.findById(taskId);
        model.addAttribute("task", existingTask);
        return "task-edit";
    }

    @PostMapping("/user/{id}/task/{taskId}/edit")
    public String editTask(@PathVariable("id") long id,
                           @PathVariable("taskId") long taskId,
                           @ModelAttribute("task") Task task,
                           @RequestParam(name = "taskTitle", required = false) String userTaskTitle,
                           @RequestParam(name = "taskContent", required = false) String userTaskContent,
                           Model model){

        task = taskService.findById(taskId);
        System.out.println(task);

        User user = userService.findUserById(id);

        task.setUser(user);
        task.setTitle(userTaskTitle);
        task.setContent(userTaskContent);

        model.addAttribute("taskTitle", userTaskTitle);
        model.addAttribute("taskContent", userTaskContent);

        model.addAttribute("id", id);
        model.addAttribute("taskId", taskId);
        model.addAttribute("task", task);
        logger.info("Updated {}", taskService.updateTask(task));

        return "redirect:/user/" + id + "/todo-list";
    }

    @GetMapping("/user/{id}/task/{taskId}/delete")
    public String deleteTask(@PathVariable("id") long id, @PathVariable("taskId") long taskId){

        logger.info("Deleted tasks {}", taskService.findById(taskId));
        taskService.deleteTaskById(taskId);

        return "redirect:/user/" + id + "/todo-list?success";
    }

    private Task getTask(String title, String content, LocalDateTime created, User user, boolean isDone){
        Task task = new Task();
        task.setTitle(title);
        task.setContent(content);
        task.setCreated(created);
        task.setUser(user);
        task.setDone(isDone);
        return task;
    }
}
