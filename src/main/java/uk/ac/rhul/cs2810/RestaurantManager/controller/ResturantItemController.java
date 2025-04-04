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

/**
 * Controller that handles item related API endpoints for the restaurant system.
 */
@RestController
@RequestMapping("/api/items")
public class ResturantItemController {

  private final ItemRepository itemRepository;

  /**
   * Constructs a ResturantItemController with the given ItemRepository.
   *
   * @param itemRepository The repository used to manage items.
   */
  @Autowired
  public ResturantItemController(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  /**
   * Retrieves a list of items filtered by their active status and optional category.
   * 
   * @param category The category to filter by.
   * @param active Whether to include only active items.
   * @return A list of matching Item objects.
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
   * Updates the active status of a specific item.
   * 
   * @param id The ID of the item to update.
   * @param update A map containing the new active status.
   * @return A ResponseEntity with the updated item, or 404/400 if not found/invalid.
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

  /**
   * Updates the details of a specific item.
   *
   * @param id The ID of the item to update.
   * @param updatedItem The item object containing updated values.
   * @return A ResponseEntity with the saved item or 404 if not found.
   */
  @PatchMapping("/{id}")
  public ResponseEntity<Item> updateItem(@PathVariable("id") Integer id, @RequestBody Item updatedItem) {
      Optional<Item> optionalItem = itemRepository.findById(id);
      if (!optionalItem.isPresent()) {
          return ResponseEntity.notFound().build();
      }
      
      Item item = optionalItem.get();
      if (updatedItem.getTitle() != null) item.setTitle(updatedItem.getTitle());
      if (updatedItem.getDescription() != null) item.setDescription(updatedItem.getDescription());
      if (updatedItem.getPrice() > 0) item.setPrice(updatedItem.getPrice());
      if (updatedItem.getCategory() != null) item.setCategory(updatedItem.getCategory());
      if (updatedItem.getCalories() > 0) item.setCalories(updatedItem.getCalories());
      if (updatedItem.getAllergens() != null) item.setAllergens(updatedItem.getAllergens());
      
      Item savedItem = itemRepository.save(item);
      return ResponseEntity.ok(savedItem);
  }
}
