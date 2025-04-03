package uk.ac.rhul.cs2810.RestaurantManager.model;

/**
 * Small class that bridges items and orders thats not a table.
 * 
 */
public class addItems {
  private int OrderID;
  private String ItemName;

  /**
   * Gets the ID of an order. .
   * 
   * @return OrderID the ID of the order.
   */
  public int getOrderID() {
    return OrderID;
  }

  /**
   * Sets the ID of an order.
   * 
   * @param id the ID to set.
   */
  public void setOrderID(int orderID) {
    this.OrderID = orderID;
  }

  /**
   * Gets the name of an item.
   * 
   * @return name The name of the item.
   */
  public String getItemName() {
    return ItemName;
  }

  /**
   * Sets the name of the item.
   * 
   * @param itemName the name to set.
   */
  public void setItemName(String itemName) {
    this.ItemName = itemName;
  }
}
