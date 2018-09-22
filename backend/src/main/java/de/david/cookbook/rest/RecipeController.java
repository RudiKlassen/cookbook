package de.david.cookbook.rest;

import de.david.cookbook.persistence.entities.Category;
import de.david.cookbook.persistence.entities.Recipe;
import de.david.cookbook.persistence.entities.User;
import de.david.cookbook.services.RecipeService;
import de.david.cookbook.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class RecipeController {

    private RecipeService recipeService;

    private UserService userService;

    @Autowired
    public RecipeController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @RequestMapping(value = "recipes", method = RequestMethod.GET)
    List<Recipe> readAllRecipes(HttpServletRequest request, @RequestParam("filter") String filterText) {
        User user = userService.getUserFromRequest(request);
        return recipeService.getAllRecipesFromUser(user, filterText);
    }


    /**
     * Erstellt ein neues Rezept.
     */
    @PostMapping(value = "recipes", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Recipe> createRecipe(HttpServletRequest request, @RequestBody @Valid RecipeRessource recipeRessource) {
        User user = userService.getUserFromRequest(request);
        return new ResponseEntity<>(recipeService.createRecipe(user, recipeRessource), HttpStatus.OK);
    }


    /**
     * Aktualisiert ein bereits existierendes Rezept.
     */
    @RequestMapping(value = "recipes/{recipeId}", method = RequestMethod.PUT)
    Recipe updateRecipe(HttpServletRequest request, @PathVariable Long recipeId,
                        @RequestBody @Valid RecipeRessource recipeRessource) {
        User user = userService.getUserFromRequest(request);
        return recipeService.updateRecipe(recipeRessource);
    }

    @RequestMapping(value = "recipes/{recipeId}", method = RequestMethod.GET)
    Recipe readRecipesById(HttpServletRequest request, @PathVariable Long recipeId) {
        User user = userService.getUserFromRequest(request);
        return recipeService.getRecipeByIdAndUser(recipeId, user);
    }

    @RequestMapping(value = "recipes/categories", method = RequestMethod.GET)
    List<Category> readAllRecipeCategories(HttpServletRequest request) {
        return recipeService.getAllCategories();
    }
}
