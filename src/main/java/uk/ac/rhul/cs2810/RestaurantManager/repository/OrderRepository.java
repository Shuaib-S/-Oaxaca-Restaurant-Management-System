package uk.ac.rhul.cs2810.RestaurantManager.repository;

import org.springframework.data.repository.CrudRepository;
import uk.ac.rhul.cs2810.RestaurantManager.model.order;

public interface OrderRepository extends CrudRepository<Order, Integer> {

}