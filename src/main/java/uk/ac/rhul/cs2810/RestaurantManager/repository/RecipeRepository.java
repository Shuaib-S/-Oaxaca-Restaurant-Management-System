package uk.ac.rhul.cs2810.RestaurantManager.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.ac.rhul.cs2810.RestaurantManager.model.Recipe;

/**
 * Repository interface for performing operations on Recipe entities.
 */
@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
  /**
   * Finds a recipe based on the name of the menu item it is associated with.
   *
   * @param itemName The name of the menu item.
   * @return An Optional containing the matching recipe.
   */
  Optional<Recipe> findByItemName(String itemName);
}
