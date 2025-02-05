package uk.ac.rhul.cs2810.RestaurantManager.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import uk.ac.rhul.cs2810.RestaurantManager.model.Item;

public interface ItemRepository extends CrudRepository<Item, Integer> {

  /**
   * Retrieves a list of items by their category.
   * 
   * @param category The category to filter items by.
   * @return A list of items matching the specified category.
   */
  List<Item> findByCategory(String category);
}
