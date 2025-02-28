package uk.ac.rhul.cs2810.RestaurantManager.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.rhul.cs2810.RestaurantManager.repository.OrderRepository;

@RestController
@RequestMapping("/api/DeleteOrder")
public class OrderDeleteController {

  private final OrderRepository orderRepository;

  @Autowired
  public OrderController(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @PostMapping
  public ResponseEntity<Integer> deleteOrder(@RequestBody Integer orderID) {
    orderRepository.deleteById(orderID);

  }
}
