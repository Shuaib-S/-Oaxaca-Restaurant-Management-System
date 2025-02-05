package uk.ac.rhul.cs2810.RestaurantManager.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
   * Retrieves all items or filters them by category if specified.
   * 
   * @param category The category to filter items by. Defaults to "all".
   * @return A list of items.
   */
  @GetMapping
  public List<Item> getItems(
      @RequestParam(value = "category", defaultValue = "all") String category) {
    if ("all".equalsIgnoreCase(category)) {
      return (List<Item>) itemRepository.findAll();
    }
    // Filter items by category
    return itemRepository.findByCategory(category);
  }
}
