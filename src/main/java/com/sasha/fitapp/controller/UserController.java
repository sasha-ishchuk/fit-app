package com.sasha.fitapp.controller;

import com.mysql.cj.util.StringUtils;
import com.sasha.fitapp.model.Task;
import com.sasha.fitapp.model.User;
import com.sasha.fitapp.service.TaskService;
import com.sasha.fitapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserController {

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
    public String getUserPage(@PathVariable(name = "id") long id){
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
    public String getToDoList(@PathVariable(name = "id") long id, Model model){
        List<Task> tasksByCreatedTime = taskService.getTaskByCreatedTime();
        model.addAttribute("tasks", tasksByCreatedTime);
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("id", id);

        return "todo-list";
    }

    @PostMapping("/user/{id}/todo-list/tasks")
    public String addTaskToList(@PathVariable(name = "id") long id, Principal principal, Model model,
                                @RequestParam(name = "taskTitle") String userTaskTitle,
                                @RequestParam(name = "taskContent") String userTaskContent){

        User currentUser = userService.findUserById(id);
        List<Task> userTasks = currentUser.getTasks();

        LocalDateTime localDateTime = LocalDateTime.now();

        if(!StringUtils.isNullOrEmpty(userTaskContent)){
            userTasks.add(getTask(userTaskTitle, userTaskContent, localDateTime, currentUser, false));
        }

        model.addAttribute("taskTitle", userTaskTitle);
        model.addAttribute("taskContent", userTaskContent);

        if(!userTasks.isEmpty()){
            taskService.addTasks(userTasks);
        }

        return "redirect:/user/" + id + "/todo-list/tasks";
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
