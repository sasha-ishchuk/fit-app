package com.sasha.fitapp.service;

import com.sasha.fitapp.model.Ingredient;
import com.sasha.fitapp.repository.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private static final Logger logger = LoggerFactory.getLogger(IngredientService.class);

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> addIngredients(List<Ingredient> ingredients){
        logger.info("Saving {}", ingredients);
        return ingredientRepository.saveAll(ingredients);
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public void deleteIngredients(List<Ingredient> ingredients){
        logger.info("Deleting {}", ingredients);
        ingredientRepository.deleteAll(ingredients);
    }
}
