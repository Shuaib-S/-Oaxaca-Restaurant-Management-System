package uk.ac.rhul.cs2810.RestaurantManager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.rhul.cs2810.RestaurantManager.model.Item;
import uk.ac.rhul.cs2810.RestaurantManager.model.Order;
import uk.ac.rhul.cs2810.RestaurantManager.model.addItems;
import uk.ac.rhul.cs2810.RestaurantManager.repository.ItemRepository;
import uk.ac.rhul.cs2810.RestaurantManager.repository.OrderRepository;

@RestController
@RequestMapping("/api/CurrentOrders")
public class OrderGetController {

  private final OrderRepository orderRepository;

  private final ItemRepository itemRepository;

  /**
   * orderRepository thaty holds the orders
   * 
   * @param orderRepository the repository object that has the order database in.
   */
  @Autowired
  public OrderGetController(OrderRepository orderRepository, ItemRepository itemRepository) {
    this.orderRepository = orderRepository;
    this.itemRepository = itemRepository;
  }

  @GetMapping("/all")
  public List<Map<String, Object>> getOrders() { // Depending on how status is handled @RequestParam
                                                 // may be needed
    List<Order> orders = (List<Order>) orderRepository.findAll();
    List<Map<String, Object>> orderMAIN = new ArrayList<>();
    for (Order order : orders) {
      Map<String, Object> order2 = new HashMap<>();
      order2.put("id", order.getId());
      order2.put("tableNumber", order.getTableNumber());
      order2.put("orderTime", order.getOrderTime());
      order2.put("timeSinceOrder", order.getTimeSinceOrder());
      order2.put("items", itemsToMap(order));
      order2.put("status", order.getStatus());
      order2.put("confirmed", order.getConfirmed());
      order2.put("paid", order.getPaid());
      orderMAIN.add(order2);
    }

    return orderMAIN;
  }

  @GetMapping("/orderItems")
  public List<Map<String, Object>> getItemNames() {
    List<Order> orders = (List<Order>) orderRepository.findAll();
    List<Map<String, Object>> orderItems = new ArrayList<>();
    for (Order order : orders) {
      Map<String, Object> order2 = new HashMap<>();
      order2.put("id", order.getId());
      order2.put("items", itemsToMap(order));
      orderItems.add(order2);
    }

    return orderItems;
  }

  @GetMapping("/table/{tableId}")
  public List<Order> getOrderByTable(@PathVariable int tableId) {
    return orderRepository.findByTableNumber(tableId);
  }

  public Map<String, Integer> itemsToMap(Order order) {
    Map<String, Integer> itemQuant = new HashMap<>();
    for (Item item : order.getItemList()) {
      itemQuant.put(item.getTitle(), itemQuant.getOrDefault(item.getTitle(), 0) + 1);
    }
    return itemQuant;
  }

  @PostMapping("/addItem")
  public ResponseEntity<?> addItem(@RequestBody addItems request) {
    int orderID = request.getOrderID();
    String itemName = request.getItemName();
    Iterable<Item> itemsIterable = this.itemRepository.findAll();
    Item item2 = new Item();
    for (Item entry : itemsIterable) {
      if (entry.getTitle().equals(itemName)) {
        item2 = entry;
        break;
      }
    }
    Optional<Order> order = this.orderRepository.findById(orderID);
    if (order.isPresent()) {
      Order order2 = order.get();
      order2.addItemToList(item2);
      this.orderRepository.save(order2);
    }
    return ResponseEntity.ok("works good my dude");
  }

  @PostMapping("/removeItem")
  public ResponseEntity<?> removeItem(@RequestBody addItems request) {
    int orderID = request.getOrderID();
    String itemName = request.getItemName();
    Iterable<Item> itemsIterable = this.itemRepository.findAll();
    Item item2 = new Item();
    for (Item entry : itemsIterable) {
      if (entry.getTitle().equals(itemName)) {
        item2 = entry;
        break;
      }
    }
    Optional<Order> order = this.orderRepository.findById(orderID);
    if (order.isPresent()) {
      Order order2 = order.get();
      List<Item> itemList = order2.getItemList();
      for (int i = 0; i < itemList.size(); i++) {
        if (itemList.contains(item2)) {
          order2.removeItemFromList(item2);
          this.orderRepository.save(order2);
          return ResponseEntity.ok("works good my dude");
        }

      }
    }
    return ResponseEntity.ok("no item in list, but ok");
  }

  @PostMapping("/confirmOrder")
  public ResponseEntity<?> confirmOrder(@RequestBody addItems request) {
    int orderID = request.getOrderID();
    boolean confirm = true;
    Optional<Order> order = this.orderRepository.findById(orderID);
    if (order.isPresent()) {
      Order order2 = order.get();
      order2.setConfirmed(confirm);
      this.orderRepository.save(order2);
      return ResponseEntity.ok("hi");
    }

    return ResponseEntity.ok("hi");
  }

}
