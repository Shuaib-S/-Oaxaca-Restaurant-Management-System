package uk.ac.rhul.cs2810.RestaurantManager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.rhul.cs2810.RestaurantManager.model.Item;
import uk.ac.rhul.cs2810.RestaurantManager.model.Order;
import uk.ac.rhul.cs2810.RestaurantManager.repository.OrderRepository;

@RestController
@RequestMapping("/api/CurrentOrders")
public class OrderGetController {

  private final OrderRepository orderRepository;

  /**
   * orderRepository thaty holds the orders
   * 
   * @param orderRepository the repository object that has the order database in.
   */
  @Autowired
  public OrderGetController(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @GetMapping
  public List<Map<String, Object>> getOrders() { // Depending on how status is handled @RequestParam may be needed
    List<Order> orders = (List<Order>) orderRepository.findAll();
    List<Map<String, Object>> orderMAIN = new ArrayList<>();
    for (Order order : orders) {
      Map<String, Object> order2 = new HashMap<>();
      order2.put("id", order.getId());
      order2.put("tableNumber", order.getTableNumber());
      order2.put("orderTime", order.getOrderTime());
      order2.put("timeSinceOrder", order.getTimeSinceOrder());
      order2.put("items", itemsToMap(order));
      orderMAIN.add(order2);
    }

    return orderMAIN;
  }

  public Map<String, Integer> itemsToMap(Order order) {
    Map<String, Integer> itemQuant = new HashMap<>();
    for (Item item : order.getItemList()) {
      itemQuant.put(item.getTitle(), itemQuant.getOrDefault(item, 0) + 1);
    }
    return itemQuant;
  }
}
