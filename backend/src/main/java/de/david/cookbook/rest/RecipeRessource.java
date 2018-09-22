package de.david.cookbook.rest;

import de.david.cookbook.persistence.entities.Ingredient;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Repräsentiert ein {@link Ingredient}
 */
public class RecipeRessource {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private int servings;

    @NotNull
    private List<IngredientRessource> ingredients;

    @NotNull
    private CategoryRessource category;

    private String imgUrl;

    public RecipeRessource() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public List<IngredientRessource> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientRessource> ingredients) {
        this.ingredients = ingredients;
    }

    public CategoryRessource getCategory() {
        return category;
    }

    public void setCategory(CategoryRessource category) {
        this.category = category;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
