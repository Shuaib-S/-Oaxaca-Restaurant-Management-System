package uk.ac.rhul.cs2810.RestaurantManager.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uk.ac.rhul.cs2810.RestaurantManager.model.Item;
import uk.ac.rhul.cs2810.RestaurantManager.repository.ItemRepository;

/**
 * The path where the maping json is sent
 */
@RestController
@RequestMapping("/api/items")
public class ResturantItemController {

  // private final ItemRepository itemRepository;
  private List<Item> menuItems = new ArrayList<>();

  @GetMapping
  public List<Item> getItems(@RequestParam(value = "category", defaultValue = "all") String category) {
    if ("all".equals(category)) {
      return menuItems;
    }
    // to-do add sorting by category
    return menuItems;
  }

  public ResturantItemController() {
    // this.itemRepository = itemRepository;
    // test data REMOVE AFTER DB IS ADDED
    menuItems.add(new Item(1, "Taco", "Hard shell taco", 5.00, "Main", 300, "Wheat, gluten, eggs"));
    menuItems.add(new Item(2, "Enchiladas Rojas",
        "Three chicken enchiladas covered in red chile sauce, topped with queso fresco.", 15.99, "Main", 600,
        "Chicken, gluten"));
    menuItems
        .add(new Item(3, "Guacamole Fresco", "Fresh avocados mixed with tomatoes, onions, cilantro, and lime juice.",
            8.99, "Starter", 150, "Avocado"));
    menuItems.add(
        new Item(4, "Churros", "Traditional Mexican pastry dusted with cinnamon sugar, served with chocolate sauce.",
            7.99, "Desserts", 250, "Wheat, gluten"));
  }
}
