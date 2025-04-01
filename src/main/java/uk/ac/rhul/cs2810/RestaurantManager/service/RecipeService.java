package uk.ac.rhul.cs2810.RestaurantManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.rhul.cs2810.RestaurantManager.model.Recipe;
import uk.ac.rhul.cs2810.RestaurantManager.repository.RecipeRepository;

import java.util.Optional;

@Service
public class RecipeService {

  @Autowired
  private RecipeRepository recipeRepository;

  public Iterable<Recipe> getAllRecipes() {
    return recipeRepository.findAll();
  }

  public Optional<Recipe> getRecipeById(Long FID) {
    return recipeRepository.findById(FID);
  }

  public Optional<Recipe> getRecipeByName(String itemName) {
    return recipeRepository.findByItemName(itemName);
  }
}
