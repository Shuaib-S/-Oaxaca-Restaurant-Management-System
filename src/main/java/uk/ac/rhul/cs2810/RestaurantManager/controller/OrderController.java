package uk.ac.rhul.cs2810.RestaurantManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.rhul.cs2810.RestaurantManager.repository.OrderRepository;
import uk.ac.rhul.cs2810.RestaurantManager.model.Order;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderRepository orderRepository;

  @Autowired
  public OrderController(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @PostMapping
  public ResponseEntity<Order> addOrder(@RequestBody Order order) { // update this with post data
    orderRepository.save(order);
    return ResponseEntity.ok(order);
  }

}
