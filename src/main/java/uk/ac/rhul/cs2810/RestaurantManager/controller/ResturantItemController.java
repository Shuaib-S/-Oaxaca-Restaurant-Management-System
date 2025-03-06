package uk.ac.rhul.cs2810.RestaurantManager.controller;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.rhul.cs2810.RestaurantManager.model.Item;
import uk.ac.rhul.cs2810.RestaurantManager.repository.ItemRepository;

@RestController
@RequestMapping("/api/items")
public class ResturantItemController {

  private final ItemRepository itemRepository;

  @Autowired
  public ResturantItemController(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  /**
   * Retrieves items filtered by active status (and category, if needed). For the waiters panel, you
   * can call /api/items?active=true to get items currently on the menu.
   */
  @GetMapping
  public List<Item> getItems(
      @RequestParam(value = "category", defaultValue = "all") String category,
      @RequestParam(value = "active", defaultValue = "true") boolean active) {
    if ("all".equalsIgnoreCase(category)) {
      return itemRepository.findByActive(active);
    }
    return itemRepository.findByCategoryAndActive(category, active);
  }

  /**
   * Updates only the active status of an item. Expected JSON payload: { "active": true } or {
   * "active": false }
   */
  @PatchMapping("/{id}/active")
  public ResponseEntity<Item> updateActiveStatus(@PathVariable("id") Integer id,
      @RequestBody Map<String, Boolean> update) {
    Optional<Item> optionalItem = itemRepository.findById(id);
    if (!optionalItem.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    Item item = optionalItem.get();
    if (update.containsKey("active")) {
      item.setActive(update.get("active"));
      Item updatedItem = itemRepository.save(item);
      return ResponseEntity.ok(updatedItem);
    }
    return ResponseEntity.badRequest().build();
  }
}
