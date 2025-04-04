package uk.ac.rhul.cs2810.RestaurantManager.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import uk.ac.rhul.cs2810.RestaurantManager.model.Stock;

/**
 * Repository interface for managing Stock entities.
 */
public interface StockRepository extends CrudRepository<Stock, Integer> {

  /**
   * Retrieves a list of items by their category.
   * 
   * @param category The category to filter items by.
   * @return A list of items matching the specified category.
   */
  List<Stock> findByCategory(String category);
}
