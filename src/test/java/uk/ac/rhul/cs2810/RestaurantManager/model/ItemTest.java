package uk.ac.rhul.cs2810.RestaurantManager.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Tester file for Item.
 */
public class ItemTest {

  private Item item = new Item(1, "Taco", "Hard shell taco", 5.00, "Main", 300, "Wheat, gluten, eggs");

  @Test
  public void getItemTest() {
    // Test 1
    assertEquals(1, item.getId());
    assertEquals("Taco", item.getTitle());
    assertEquals("Hard shell taco", item.getDescription());
    assertEquals(5.00, item.getPrice());
    assertEquals("Main", item.getCategory());
    assertEquals(300, item.getCalories());
    assertEquals("Wheat, gluten, eggs", item.getAllergens());
  }

  @Test
  public void setItemTest() {
    item.setId(2);
    item.setTitle("Burrito");
    item.setDescription("Soft burrito");
    item.setPrice(8.00);
    item.setCategory("Main");
    item.setCalories(600);
    item.setAllergens("Wheat");
    assertEquals(2, item.getId());
    assertEquals("Burrito", item.getTitle());
    assertEquals("Soft burrito", item.getDescription());
    assertEquals(8.00, item.getPrice());
    assertEquals("Main", item.getCategory());
    assertEquals(600, item.getCalories());
    assertEquals("Wheat", item.getAllergens());
  }

}
