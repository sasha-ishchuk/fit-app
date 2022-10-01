package com.sasha.fitapp.service;

import com.sasha.fitapp.model.NutritionValue;
import com.sasha.fitapp.repository.NutritionValueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NutritionValueService {

    private static final Logger logger = LoggerFactory.getLogger(NutritionValueService.class);

    private final NutritionValueRepository nutritionValueRepository;

    public NutritionValueService(NutritionValueRepository nutritionValueRepository) {
        this.nutritionValueRepository = nutritionValueRepository;
    }

    public NutritionValue addNutritionValue(NutritionValue value){
        logger.info("Saving {}", value);
        return nutritionValueRepository.save(value);
    }

    public List<NutritionValue> getAllNutritionValues(){
        return nutritionValueRepository.findAll();
    }
}
