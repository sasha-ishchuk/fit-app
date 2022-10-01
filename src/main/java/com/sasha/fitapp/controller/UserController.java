package com.sasha.fitapp.controller;

import com.sasha.fitapp.model.Ingredient;
import com.sasha.fitapp.model.Recipe;
import com.sasha.fitapp.model.Task;
import com.sasha.fitapp.model.User;
import com.sasha.fitapp.service.IngredientService;
import com.sasha.fitapp.service.RecipeService;
import com.sasha.fitapp.service.TaskService;
import com.sasha.fitapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final TaskService taskService;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public UserController(UserService userService, TaskService taskService, RecipeService recipeService, IngredientService ingredientService) {
        this.userService = userService;
        this.taskService = taskService;
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/user")
    public String redirectUserPage(Principal principal){
        return "redirect:/user/" + getCurrentLoginUserId(principal);
    }

    @GetMapping("/user/{id}")
    public String getUserPage(@PathVariable("id") long id){
        return "user";
    }

//    public static String LOAD_DIR = System.getProperty("user.dir") + "/users_photo";
//
//    @PostMapping("/load") public String uploadImage(@RequestParam("image") MultipartFile file,
//                                                    Model model) throws IOException {
//        StringBuilder fileName = new StringBuilder();
//        Path fileNameAndPath = Paths.get(LOAD_DIR, file.getOriginalFilename());
//        fileName.append(file.getOriginalFilename());
//        Files.write(fileNameAndPath, file.getBytes());
//        model.addAttribute("msg", "Loaded photo: " + fileName);
//        return "user";
//    }

    @GetMapping("/todo-list")
    public String redirectToDoList(Principal principal){
        return "redirect:/user/" + getCurrentLoginUserId(principal) + "/todo-list";
    }

    @GetMapping("/recipes")
    public String redirectToRecipes(Principal principal){
        return "redirect:/user/" + getCurrentLoginUserId(principal) + "/recipes";
    }

    @GetMapping("/bmi")
    public String getBMITable(){
        return "bmi";
    }

    @DeleteMapping("/user/{id}/delete")
    public String deleteUserAccount(@PathVariable("id") long id, Model model){
        model.addAttribute("id", id);

        List<Task> tasks = taskService.getTaskByCreatedTime();
        tasks.removeIf(task -> task.getUser().getId() != id);
        logger.info("Deleted user tasks {}", tasks);
        taskService.deleteTasks(tasks);

        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        ingredients.removeIf(ingredient -> ingredient.getRecipe().getUser().getId() != id);
        logger.info("Deleted user ingredients {}", ingredients);
        ingredientService.deleteIngredients(ingredients);

        List<Recipe> recipes = recipeService.getAllRecipes();
        recipes.removeIf(recipe -> recipe.getUser().getId() != id);
        logger.info("Deleted user recipes {}", recipes);
        recipeService.deleteRecipes(recipes);


        logger.info("Deleted user {}", userService.findUserById(id));
        userService.deleteUser(id);
        return "redirect:/?success";
    }

    private long getCurrentLoginUserId(Principal principal){
        User currentUser = userService.findUserByEmail(principal.getName());
        return currentUser.getId();
    }
}
