package uk.ac.rhul.cs2810.RestaurantManager.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import uk.ac.rhul.cs2810.RestaurantManager.model.Order;
import uk.ac.rhul.cs2810.RestaurantManager.model.Item;
import org.junit.jupiter.api.Test;

public class OrderTest {

  private Order testOrder = new Order();
  private Item item = new Item(1, "Taco", "Hard shell taco", 5.00, "Main", 300, "Wheat, gluten, eggs");
  private List<Item> items = new ArrayList<Item>();

  // Test 1 Tests the order time
  @Test
  public void orderTimeTest() {
    this.testOrder.addItemToList(item);
    assertEquals(this.testOrder.getOrderTime(), this.testOrder.getOrderTime());
  }

  @Test
  public void OrderTest() {
    this.testOrder.addItemToList(item);
    this.testOrder.setId(1);
    this.items.add(item);
    assertEquals(1, this.testOrder.getId());
    assertEquals(items, this.testOrder.getItemList());
  }
}
