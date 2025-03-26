package uk.ac.rhul.cs2810.RestaurantManager.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class addItemsTest {

  // Test 1
  @Test
  public void orderIDTest() {
    addItems itemsAdded = new addItems();
    itemsAdded.setOrderID(1);
    itemsAdded.setItemName("Beef");
    assertEquals(1, itemsAdded.getOrderID());
    assertEquals("Beef", itemsAdded.getItemName());
  }

}
