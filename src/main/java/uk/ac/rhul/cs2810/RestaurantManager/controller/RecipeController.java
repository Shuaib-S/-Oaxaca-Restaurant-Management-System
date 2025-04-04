package uk.ac.rhul.cs2810.RestaurantManager.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.rhul.cs2810.RestaurantManager.model.Recipe;
import uk.ac.rhul.cs2810.RestaurantManager.service.RecipeService;

/**
 * controller for managing recipe related end points.
 */
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

  @Autowired
  private RecipeService recipeService;

  /**
   * Default class required by spring.
   */
  public RecipeController() {}

  /**
   * Retrieves all recipes from the database.
   *
   * @return A list of all Recipe objects.
   */
  @GetMapping
  public List<Recipe> getAllRecipes() {
    return (List<Recipe>) recipeService.getAllRecipes();
  }

  /**
   * Retrieves a recipe by its unique ID.
   *
   * @param FID The unique identifier of the recipe.
   * @return A ResponseEntity containing the recipe if found, or 404 error if not.
   */
  @GetMapping("/{FID}")
  public ResponseEntity<Recipe> getRecipeById(@PathVariable Long FID) {
    return recipeService.getRecipeById(FID)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  /**
   * Retrieves a recipe by the associated item's name.
   *
   * @param itemName The name of the item for which to find the recipe.
   * @return A ResponseEntity containing the recipe if found, or 404 error if not.
   */
  @GetMapping("/name/{itemName}")
  public ResponseEntity<Recipe> getRecipeByName(@PathVariable String itemName) {
    return recipeService.getRecipeByName(itemName)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
