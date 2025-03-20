package uk.ac.rhul.cs2810.RestaurantManager.model;

/**
 * Small class that bridges items and orders thats not a table.
 * It literally just has getters and setters
 */
public class addItems {
  private int OrderID;
  private String ItemName;

  public int getOrderID() {
    return OrderID;
  }

  public void setOrderID(int orderID) {
    this.OrderID = orderID;
  }

  public String getItemName() {
    return ItemName;
  }

  public void setItemName(String itemName) {
    this.ItemName = itemName;
  }
}
