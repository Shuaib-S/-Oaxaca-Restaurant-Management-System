package uk.ac.rhul.cs2810.RestaurantManager.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import uk.ac.rhul.cs2810.RestaurantManager.model.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {

  /**
   * Find order by table number
   * 
   * @param tableNumber the table number
   * @return The order by table number
   */
  List<Order> findByTableNumber(int tableNumber);
}