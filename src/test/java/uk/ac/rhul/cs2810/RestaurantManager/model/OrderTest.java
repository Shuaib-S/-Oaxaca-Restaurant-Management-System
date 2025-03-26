package uk.ac.rhul.cs2810.RestaurantManager.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import java.util.ArrayList;
import java.util.List;
import java.time.Duration;
import java.time.LocalDateTime;

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
    /**
     * This is really hard to test as when you run this code it will generate a
     * order time stamp and its impossible to create a similar variable
     * with the exact same time stamp to the milisecond as the code executes
     * line-by-line so there will be some delay ( however small )
     * So we're testing the function calls and the type that is crated
     */
    assertEquals(this.testOrder.getOrderTime(), this.testOrder.getOrderTime());
    assertInstanceOf(LocalDateTime.class, this.testOrder.getOrderTime());

    /**
     * Similarly to the last testing for the duration type is about the best I can
     * do
     * //assertEquals(this.testOrder.getTimeSinceOrder(),
     * this.testOrder.getTimeSinceOrder());
     * Even doing this returns [ERROR]
     * uk.ac.rhul.cs2810.RestaurantManager.model.OrderTest.orderTimeTest --
     * Time elapsed: 0.015 s <<< FAILURE! org.opentest4j.AssertionFailedError:
     * expected: <PT0.0020009S> but was: <PT0.0030005S>
     * Literal Milisecond appart because of how Java runs methods.
     */

    assertInstanceOf(Duration.class, this.testOrder.getTimeSinceOrder());
  }

  // Test 2 Tests wheather the items are in the order
  @Test
  public void OrderTest() {
    Order testOrder2 = new Order(10, this.items, 3);
    this.testOrder.addItemToList(item);
    this.testOrder.setId(1);
    this.items.add(item);
    assertEquals(1, this.testOrder.getId());
    assertEquals(items, this.testOrder.getItemList());
    assertEquals(10, testOrder2.getId());
  }

  // Test 3
  @Test
  public void RemoveItemTest() {
    this.testOrder.addItemToList(item);
    this.testOrder.setId(1);
    this.items.add(item);
    assertEquals(1, this.testOrder.getId());
    assertEquals(items, this.testOrder.getItemList());
    // Previous from Test 2 to set up for the test
    this.testOrder.removeItemFromList(item);
    this.items.clear();
    assertEquals(items, this.testOrder.getItemList());
  }

  // Test 4
  @Test
  public void TestConfirmedAndStatus() {
    this.testOrder.setStatus("Pending");
    assertEquals("Pending", this.testOrder.getStatus());
    this.testOrder.setConfirmed(true);
    assertEquals(true, this.testOrder.getConfirmed());
    this.testOrder.setConfirmed(false);
    assertEquals(false, this.testOrder.getConfirmed());
  }

  // Test 5
  @Test
  public void getTableSetTableTest() {
    this.testOrder.setTableNumber(10);
    assertEquals(10, this.testOrder.getTableNumber());
  }

  // Test 6
  @Test
  public void setItemListTest() {
    items.add(item);
    items.add(item);
    this.testOrder.setItemList(items);
    assertEquals(items, this.testOrder.getItemList());
  }

}
