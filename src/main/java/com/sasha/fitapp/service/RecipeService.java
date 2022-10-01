package com.sasha.fitapp.service;

import com.sasha.fitapp.model.Recipe;
import com.sasha.fitapp.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private static final Logger logger = LoggerFactory.getLogger(RecipeService.class);
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes(){
        return recipeRepository.findAll();
    }

    public List<Recipe> addRecipes(List<Recipe> recipes){
        logger.info("Saving {}", recipes);
        return recipeRepository.saveAll(recipes);
    }

    public Recipe findRecipeById(long id){
        return recipeRepository.findById(id).orElseThrow();
    }

    public void deleteRecipes(List<Recipe> recipes){
        logger.info("Deleting {}", recipes);
        recipeRepository.deleteAll(recipes);
    }
}
