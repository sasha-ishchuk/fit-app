package com.sasha.fitapp.controller;

import com.sasha.fitapp.model.*;
import com.sasha.fitapp.service.IngredientService;
import com.sasha.fitapp.service.NutritionValueService;
import com.sasha.fitapp.service.RecipeService;
import com.sasha.fitapp.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RecipesController {

    private static final Logger logger = LoggerFactory.getLogger(RecipesController.class);
    private final UserService userService;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final NutritionValueService nutritionValueService;

    public RecipesController(UserService userService, RecipeService recipeService, IngredientService ingredientService, NutritionValueService nutritionValueService) {
        this.userService = userService;
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.nutritionValueService = nutritionValueService;
    }

    @GetMapping("/user/{id}/recipes")
    public String getRecipeBook(@PathVariable("id") long id, Model model){
        List<Recipe> userRecipes = recipeService.getAllRecipes();
        userRecipes.removeIf(recipe -> recipe.getUser().getId() != id);

        List<Ingredient> userRecipeIngredients = ingredientService.getAllIngredients();
        userRecipeIngredients.removeIf(ingredient -> ingredient.getRecipe().getUser().getId() != id);

        List<NutritionValue> userRecipeNutritionValues = nutritionValueService.getAllNutritionValues();
        userRecipeNutritionValues.removeIf(value -> value.getRecipe().getUser().getId() != id);

        model.addAttribute("id", id);
        model.addAttribute("recipes", userRecipes);
        model.addAttribute("ingredients", userRecipeIngredients);
        model.addAttribute("nutritionValues", userRecipeNutritionValues);

        return "recipes";
    }

    @PostMapping( "/user/{id}/recipes/add")
    public String addRecipe(@PathVariable(name = "id") long id, Model model,
                                @RequestParam(name = "recipeTitle", required = false) String userRecipeTitle,
                                @RequestParam(name = "recipeImage", required = false) String userRecipeImage,
                                @RequestParam(name = "prepareTime", required = false, defaultValue = "0") int prepareTime,
                                @RequestParam(name = "cookTime", required = false, defaultValue = "0") int cookTime,
                                @RequestParam(name = "servings", required = false, defaultValue = "0") int servings,
                                @RequestParam(name = "recipeProcess", required = false) String userRecipeProcess){

        List<Recipe> userRecipes = new ArrayList<>();

        User currentUser = userService.findUserById(id);

        if(StringUtils.isNotEmpty(userRecipeTitle) && StringUtils.isNotEmpty(userRecipeProcess)
                && StringUtils.isNotEmpty(userRecipeImage) && prepareTime != Integer.MIN_VALUE
                && cookTime != Integer.MIN_VALUE && servings != Integer.MIN_VALUE){
            userRecipes.add(getRecipe(userRecipeTitle, userRecipeImage, prepareTime, cookTime,
                    servings, userRecipeProcess, currentUser));
        }

        model.addAttribute("recipeTitle", userRecipeTitle);
        model.addAttribute("recipeImage", userRecipeImage);
        model.addAttribute("prepareTime", prepareTime);
        model.addAttribute("cookTime", cookTime);
        model.addAttribute("servings", servings);
        model.addAttribute("recipeProcess", userRecipeProcess);

        if(!userRecipes.isEmpty()){
            logger.info("Saved {}", recipeService.addRecipes(userRecipes));
        }
        model.addAttribute("recipes", userRecipes);

        return "redirect:/user/" + id + "/recipes";
    }

    @GetMapping("/user/{id}/recipes/{recipeId}/add-info")
    public String getAddIngredientPage(@PathVariable(name = "id") long id,
                                       @PathVariable(name = "recipeId") long recipeId,
                                       Model model){

        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        ingredients.removeIf(ingredient -> ingredient.getRecipe().getUser().getId() != id
                || ingredient.getRecipe().getId() != recipeId);

        model.addAttribute("recipes", ingredients);
        model.addAttribute("id", id);
        model.addAttribute("recipeId", recipeId);

        return "recipe-add-info";
    }

    @PostMapping( "/user/{id}/recipes/{recipeId}/ingredient/add")
    public String addIngredient(@PathVariable(name = "id") long id,
                                        @PathVariable(name = "recipeId") long recipeId, Model model,
                                        @RequestParam(name = "ingredientDecs", required = false) String description,
                                        @RequestParam(name = "ingredientAmount", required = false, defaultValue = "0") BigDecimal amount,
                                        @RequestParam(name = "unitOfMeasure", required = false) UnitOfMeasurement unit){

        List<Ingredient> recipeIngredients = new ArrayList<>();
        Recipe currentRecipe = recipeService.findRecipeById(recipeId);

        if(StringUtils.isNotEmpty(description) && unit != null && amount.intValue() >= 0){
            recipeIngredients.add(getIngredient(description, amount, unit, currentRecipe));
        }

        model.addAttribute("ingredientDesc", description);
        model.addAttribute("ingredientAmount", amount);
        model.addAttribute("unitOfMeasure", unit);

        if(!recipeIngredients.isEmpty()){
            logger.info("Saved {}", ingredientService.addIngredients(recipeIngredients));
        }
        model.addAttribute("ingredients", recipeIngredients);

        return "redirect:/user/" + id + "/recipes";
    }

    @PostMapping( "/user/{id}/recipes/{recipeId}/nutrition-value/add")
    public String addNutritionValue(@PathVariable(name = "id") long id,
                @PathVariable(name = "recipeId") long recipeId, Model model,
                @RequestParam(name = "recipeCalories", required = false, defaultValue = "0") BigDecimal calories,
                @RequestParam(name = "recipeProteins", required = false, defaultValue = "0") BigDecimal proteins,
                @RequestParam(name = "recipeFats", required = false, defaultValue = "0") BigDecimal fats,
                @RequestParam(name = "recipeCarbohydrates", required = false, defaultValue = "0") BigDecimal carbohydrates){

        NutritionValue nutritionValue = new NutritionValue();
        Recipe currentRecipe = recipeService.findRecipeById(recipeId);

        if(calories.intValue() >= 0 && proteins.intValue() >= 0
                && fats.intValue() >= 0 && carbohydrates.intValue() >= 0){
            nutritionValue.setCalories(calories);
            nutritionValue.setProteins(proteins);
            nutritionValue.setFats(fats);
            nutritionValue.setCarbohydrates(carbohydrates);
            nutritionValue.setRecipe(currentRecipe);
        }

        model.addAttribute("recipeCalories", calories);
        model.addAttribute("recipeProteins", proteins);
        model.addAttribute("recipeFats", fats);
        model.addAttribute("recipeCarbohydrates", carbohydrates);

        logger.info("Saved {}", nutritionValueService.addNutritionValue(nutritionValue));
        model.addAttribute("nutritionValue", nutritionValue);

        return "redirect:/user/" + id + "/recipes";
    }

    private Recipe getRecipe(String title, String imageUrl, int prepareTime, int cookTime,
                             int servings, String process, User user){
        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setImageUrl(imageUrl);
        recipe.setPrepareTime(prepareTime);
        recipe.setCookTime(cookTime);
        recipe.setServings(servings);
        recipe.setCookProcess(process);
        recipe.setUser(user);
        return recipe;
    }

    private Ingredient getIngredient(String description, BigDecimal amount,
                                  UnitOfMeasurement unit, Recipe recipe){
        Ingredient ingredient = new Ingredient();
        ingredient.setDescription(description);
        ingredient.setAmount(amount);
        ingredient.setUnitOfMeasurement(unit);
        ingredient.setRecipe(recipe);
        return ingredient;
    }
}
