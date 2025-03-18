package uk.ac.rhul.cs2810.RestaurantManager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.rhul.cs2810.RestaurantManager.model.Item;
import uk.ac.rhul.cs2810.RestaurantManager.model.Order;
import uk.ac.rhul.cs2810.RestaurantManager.repository.OrderRepository;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderRepository orderRepository;

  @Autowired
  public OrderController(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  /**
   * Response from the front end.
   * 
   * @param order Change this with whatever comes from frontend
   * @return The response code.
   */
  @PostMapping
  public ResponseEntity<Map<String, Object>> addOrder(@RequestBody Map<String, Object> order) {

    Order orderItem = new Order();
    List<Map<String, Object>> itemList = (List<Map<String, Object>>) order.get("itemList");
    int tableNum = (int) order.get("tableNumber");
    List<Item> items = new ArrayList<>();
    for (Map<String, Object> itemEntry : itemList) {
      Map<String, Object> itemMap = (Map<String, Object>) itemEntry.get("item");
      Integer quantity = (Integer) itemEntry.get("quantity");

      Item item = new Item();
      item.setId((Integer) itemMap.get("id"));
      item.setTitle((String) itemMap.get("title"));
      item.setDescription((String) itemMap.get("description"));
      item.setPrice(Double.valueOf(itemMap.get("price").toString()));
      item.setCategory((String) itemMap.get("category"));
      item.setCalories((Integer) itemMap.get("calories"));
      item.setAllergens((String) itemMap.get("allergens"));
      int i = 0;
      while (i < quantity) {
        orderItem.addItemToList(item);
        i++;
      }

    }
    orderItem.setTableNumber(tableNum);
    orderItem.setStatus("pending");
    this.orderRepository.save(orderItem);
    return ResponseEntity.ok(order);
  }

  /**
   * someone add a proper javadoc comment here please lol it updates the status of an order
   */
  @PatchMapping("/{id}/status")
  public ResponseEntity<?> updateOrderStatus(@PathVariable("id") Integer id,
      @RequestBody Map<String, String> update) {

    Optional<Order> optionalOrder = orderRepository.findById(id);
    if (!optionalOrder.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    String status = update.get("status");
    Order order = optionalOrder.get();
    order.setStatus(status);
    Order updatedOrder = orderRepository.save(order);
    return ResponseEntity.ok(updatedOrder);
  }
}
