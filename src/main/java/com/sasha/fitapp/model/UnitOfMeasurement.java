package com.sasha.fitapp.model;

public enum UnitOfMeasurement {
    LITER("l"),
    MILLILITER("ml"),

    KILOGRAM("kg"),
    GRAM("g"),
    MILLIGRAM("mg"),

    TABLESPOON("tbsp."),
    TEASPOON("tsp."),

    CUP("cup"),
    HALF_CUP("1/2 cup");

    private final String displayValue;

    UnitOfMeasurement(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
