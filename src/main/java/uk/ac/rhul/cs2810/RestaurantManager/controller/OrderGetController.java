package uk.ac.rhul.cs2810.RestaurantManager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.rhul.cs2810.RestaurantManager.model.Item;
import uk.ac.rhul.cs2810.RestaurantManager.model.Order;

import uk.ac.rhul.cs2810.RestaurantManager.repository.OrderRepository;

@RestController
@RequestMapping("/api/CurrentOrders")
public class OrderGetController {

  private final OrderRepository orderRepository;

  @Autowired
  public OrderGetController(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @GetMapping
  public List<Order> getOrders() { // Depending on how status is handled @RequestParam may be needed
    return (List<Order>) orderRepository.findAll();
  }
}
