package de.david.cookbook.rest;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class IngredientRessource{

    @NotNull
    private double amount;

    @NotNull
    private String unit;

    @NotNull
    private String name;

    public IngredientRessource() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
