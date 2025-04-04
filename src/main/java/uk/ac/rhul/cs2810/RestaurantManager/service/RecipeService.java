package uk.ac.rhul.cs2810.RestaurantManager.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.rhul.cs2810.RestaurantManager.model.Recipe;
import uk.ac.rhul.cs2810.RestaurantManager.repository.RecipeRepository;

/**
 * Service class for handling business logic related to recipes.
 */
@Service
public class RecipeService {

  /**
   * Repository for performing database operations on recipe entities.
   */
  @Autowired
  private RecipeRepository recipeRepository;

  /**
   * Default constructor required by spring.
   */
  public RecipeService() {}

  /**
   * Retrieves all recipes from the database.
   *
   * @return An Iterable containing all recipes.
   */
  public Iterable<Recipe> getAllRecipes() {
    return recipeRepository.findAll();
  }

  /**
   * Retrieves a recipe by its id.
   *
   * @param FID The id of the recipe.
   * @return An Optional containing the recipe if found, or empty if not.
   */
  public Optional<Recipe> getRecipeById(Long FID) {
    return recipeRepository.findById(FID);
  }

  /**
   * Retrieves a recipe by the associated item's name.
   *
   * @param itemName The name of the item linked to the recipe.
   * @return An Optional containing the recipe if found, or empty if not.
   */
  public Optional<Recipe> getRecipeByName(String itemName) {
    return recipeRepository.findByItemName(itemName);
  }
}
