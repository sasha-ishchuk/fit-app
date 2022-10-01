package com.sasha.fitapp.repository;

import com.sasha.fitapp.model.NutritionValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NutritionValueRepository extends JpaRepository<NutritionValue, Long> {
}