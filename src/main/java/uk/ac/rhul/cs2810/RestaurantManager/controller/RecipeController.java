package uk.ac.rhul.cs2810.RestaurantManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.rhul.cs2810.RestaurantManager.model.Recipe;
import uk.ac.rhul.cs2810.RestaurantManager.service.RecipeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

  @Autowired
  private RecipeService recipeService;

  @GetMapping
  public List<Recipe> getAllRecipes() {
    return (List<Recipe>) recipeService.getAllRecipes();
  }

  @GetMapping("/{FID}")
  public ResponseEntity<Recipe> getRecipeById(@PathVariable Long FID) {
    return recipeService.getRecipeById(FID)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/name/{itemName}")
  public ResponseEntity<Recipe> getRecipeByName(@PathVariable String itemName) {
    return recipeService.getRecipeByName(itemName)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
