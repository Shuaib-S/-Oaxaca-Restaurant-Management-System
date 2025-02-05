package uk.ac.rhul.cs2810.RestaurantManager.repository;

import org.springframework.data.repository.CrudRepository;
import uk.ac.rhul.cs2810.RestaurantManager.model.Item;

/**
 * A repository that manages Restaurant Menu Items.
 */
public interface ItemRepository extends CrudRepository<Item, Integer> {
}
