package uk.ac.rhul.cs2810.RestaurantManager.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders") // Change this to the table name if not orders
public class Order {

  @Id
  int id;

  @OneToMany
  private List<Item> itemList;

  private int tableNumber;

  // Probably need a time stamp

  /**
   * Order Contrstructor
   * 
   * @param id       The Id for the order.
   * @param itemList The list of items that the order has.
   * @param tableNo  The table number for the order (Required for UserStory)
   */
  public Order(int id, List<Item> itemList, int tableNo) {
    this.id = id;
    this.itemList = itemList;
    this.tableNumber = tableNo;
  }

  public Order() {

  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public List<Item> getItemList() {
    return itemList;
  }

  public void setItemList(List<Item> itemList) {
    this.itemList = itemList;
  }

  public int getTableNumber() {
    return tableNumber;
  }

  public void setTableNumber(int tableNumber) {
    this.tableNumber = tableNumber;
  }
}
