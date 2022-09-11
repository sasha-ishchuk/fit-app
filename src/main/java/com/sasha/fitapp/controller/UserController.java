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

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final TaskService taskService;

    public UserController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/user")
    public String redirectUserPage(Principal principal){
        return "redirect:/user/" + getCurrentLoginUserId(principal);
    }

    @GetMapping("/user/{id}")
    public String getUserPage(@PathVariable("id") long id){
        return "user";
    }

    @GetMapping("/bmi")
    public String getBMITable(){
        return "bmi";
    }

    @GetMapping("/todo-list")
    public String redirectToDoList(Principal principal){
        return "redirect:/user/" + getCurrentLoginUserId(principal) + "/todo-list";
    }

    @GetMapping("/user/{id}/todo-list")
    public String getToDoList(@PathVariable("id") long id, Model model){
        List<Task> tasksByCreatedTime = taskService.getTaskByCreatedTime();

        tasksByCreatedTime.removeIf(task -> task.getUser().getId() != id);

        model.addAttribute("tasks", tasksByCreatedTime);
        model.addAttribute("id", id);

        return "todo-list";
    }

    @PostMapping( "/user/{id}/todo-list/tasks")
    public String addTaskToList(@PathVariable(name = "id") long id, Model model,
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

    @DeleteMapping("/user/{id}/delete")
    public String deleteUserAccount(@PathVariable("id") long id, Model model){
        model.addAttribute("id", id);

        List<Task> tasksByCreatedTime = taskService.getTaskByCreatedTime();
        tasksByCreatedTime.removeIf(task -> task.getUser().getId() != id);

        logger.info("Deleted tasks {}", tasksByCreatedTime);
        taskService.deleteTasksByUser(tasksByCreatedTime);

        logger.info("Deleted user {}", userService.findUserById(id));
        userService.deleteUser(id);
        return "redirect:/?success";
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

    private long getCurrentLoginUserId(Principal principal){
        User currentUser = userService.findUserByEmail(principal.getName());
        return currentUser.getId();
    }
}
