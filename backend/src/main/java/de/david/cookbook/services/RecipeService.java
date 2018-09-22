package de.david.cookbook.services;

import de.david.cookbook.persistence.entities.Category;
import de.david.cookbook.persistence.entities.Ingredient;
import de.david.cookbook.persistence.entities.Recipe;
import de.david.cookbook.persistence.entities.User;
import de.david.cookbook.persistence.repositories.CategoryRepository;
import de.david.cookbook.persistence.repositories.IngredientRepository;
import de.david.cookbook.persistence.repositories.RecipeRepository;
import de.david.cookbook.rest.IngredientRessource;
import de.david.cookbook.rest.RecipeRessource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;
    private CategoryRepository categoryRepository;
    private IngredientRepository ingredientRepository;
    private PermissionService permissionService;

    @Autowired
    public RecipeService(
            RecipeRepository recipeRepository,
            CategoryRepository categoryRepository,
            IngredientRepository ingredientRepository,
            PermissionService permissionService) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.ingredientRepository = ingredientRepository;
        this.permissionService = permissionService;
    }

    public List<Recipe> getAllRecipesFromUser(User user, String filterText) {
        List<Recipe> recipesOfUser = recipeRepository.findByAuthor(user);

        if (StringUtils.isNotEmpty(filterText)) {
            String filterTextLowered = filterText.toLowerCase();
            recipesOfUser = recipesOfUser.stream()
                    .filter(recipe ->
                            recipe.getCategory().getName().toLowerCase().contains(filterTextLowered) ||
                                    recipe.getName().toLowerCase().contains(filterTextLowered) ||
                                    recipe.getAuthor().getFirstName().toLowerCase().contains(filterTextLowered) ||
                                    recipe.getAuthor().getLastName().toLowerCase().contains(filterTextLowered) ||
                                    recipe.getAuthor().getEmail().toLowerCase().contains(filterTextLowered)
                    )
                    .collect(Collectors.toList());
        }

        return recipesOfUser;
    }

    public Recipe getRecipeByIdAndUser(Long id, User user) {
        Recipe recipe = recipeRepository.findOne(id);

        if (permissionService.isUserAllowedToReadRecipe(user, recipe)) {
            return recipeRepository.findOne(id);
        } else {
            return null; // TODO: throw Permission Exception
        }
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Recipe createRecipe(User user, RecipeRessource recipeRessource) {
        return create(user, recipeRessource);
    }

    /**
     * Aktualisiert ein {@link Recipe} indem der Inhalt von recipeRessource auf recipe überschrieben wird.
     *
     * @param recipeId        Das zu aktualisierende Rezept
     * @param recipeRessource Enthält das aktuelle Rezept
     * @return Das aktualisierte Rezept
     */
    public Recipe updateRecipe( RecipeRessource recipeRessource) {
        Recipe recipe = recipeRepository.findOne(recipeRessource.getId());
        recipeRepository.save(update(recipe, recipeRessource));
        return update(recipe, recipeRessource);
    }

    /**
     * Speichert den Inhalt der {@link IngredientRessource} Liste in der Datenbank und gibt die gespeicherten {@link Ingredient} als Liste Entity Liste zurück.
     *
     * @param ingredients Zutaten als {@link IngredientRessource} Liste
     * @return Liste gespeicherter {@link Ingredient}
     */
    private List<Ingredient> saveIngredients(List<IngredientRessource> ingredients) {
        List<Ingredient> newIngredients = new ArrayList<>();

        for (IngredientRessource ingredient : ingredients) {
            Ingredient newIngredient = new Ingredient(ingredient.getAmount(), ingredient.getUnit(), ingredient.getName());
            newIngredients.add(ingredientRepository.save(newIngredient));
        }

        return newIngredients;
    }

    /**
     * Erstellt aus einem RecipeRessource eine Recipe und speichert den übergebenen user als Authoren.
     */
    private Recipe create(User user, RecipeRessource recipeRessource) {

        String imgUrl = recipeRessource.getImgUrl();
        Category category = categoryRepository.findOne(recipeRessource.getCategory().getId());

        Recipe newRecipe = new Recipe(recipeRessource.getName()//
                , user //
                , recipeRessource.getServings()
                , recipeRessource.getDescription() //
                , category //
                , saveIngredients(recipeRessource.getIngredients())
        );

        //Setze optionale Felder
        newRecipe.setImageURL(imgUrl);

        return recipeRepository.save(newRecipe);
    }

    private Recipe update(Recipe recipe, RecipeRessource recipeRessource) {
        recipe.setName(recipeRessource.getName());
        recipe.setDescription(recipeRessource.getDescription());
        recipe.setServings(recipeRessource.getServings());

        Category category = categoryRepository.findOne(recipe.getCategory().getId());
        recipe.setCategory(category);
        recipe.setIngredients(saveIngredients(recipeRessource.getIngredients()));
        //TODO wie wird sichergestellt, dass nicht mehr zugewiesene Ingredients auch gelöscht werden?
        return recipe;
    }
}
