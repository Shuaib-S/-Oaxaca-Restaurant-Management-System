package uk.ac.rhul.cs2810.RestaurantManager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.ac.rhul.cs2810.RestaurantManager.model.Recipe;

import java.util.Optional;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
  Optional<Recipe> findByItemName(String itemName);
}
