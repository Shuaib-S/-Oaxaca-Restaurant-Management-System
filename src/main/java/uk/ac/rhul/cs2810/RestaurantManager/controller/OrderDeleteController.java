package uk.ac.rhul.cs2810.RestaurantManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.rhul.cs2810.RestaurantManager.repository.OrderRepository;

/**
 * A controller for deleting orders in the restaurant system. This controller provides an end point
 * to delete an order from the database.
 */
@RestController
@RequestMapping("/api/DeleteOrder")
public class OrderDeleteController {

  private final OrderRepository orderRepository;

  /**
   * Constructor for OrderDeleteController.
   * 
   * @param orderRepository The repository used to interact with the orders in the database.
   */
  @Autowired
  public OrderDeleteController(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  /**
   * Deleting an order method
   * 
   * @param orderID the id of the order in the database
   * @return if the delete was ok
   */
  @PostMapping
  public ResponseEntity<Integer> deleteOrder(@RequestBody Integer orderID) {
    orderRepository.deleteById(orderID);
    return ResponseEntity.ok(orderID);
  }
}
