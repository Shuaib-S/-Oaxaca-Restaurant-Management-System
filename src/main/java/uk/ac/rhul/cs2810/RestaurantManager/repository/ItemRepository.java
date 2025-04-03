package uk.ac.rhul.cs2810.RestaurantManager.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import uk.ac.rhul.cs2810.RestaurantManager.model.Item;

/**
 * Repository interface for managing Item entities.
 * 
 * Provides methods for retrieving items based on category and availability status.
 */
public interface ItemRepository extends CrudRepository<Item, Integer> {

  /**
   * Retrieves a list of items by their category.
   * 
   * @param category The category to filter items by.
   * @return A list of items matching the specified category.
   */
  List<Item> findByCategory(String category);

  /**
   * Retrieves a list of items by their active status.
   *
   * @param active The active status to filter items by.
   * @return A list of items matching the specified active status.
   */
  List<Item> findByActive(boolean active);

  /**
   * Retrieves a list of items by category and active status.
   *
   * @param category The category to filter items by.
   * @param active The active status to filter items by.
   * @return A list of items matching both category and active status.
   */
  List<Item> findByCategoryAndActive(String category, boolean active);
}
