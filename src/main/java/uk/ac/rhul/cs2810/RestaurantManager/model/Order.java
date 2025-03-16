package uk.ac.rhul.cs2810.RestaurantManager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // Change this to the table name if not orders
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;

  @OneToMany
  private List<Item> itemList;

  private int tableNumber;

  private LocalDateTime orderTime;

  private String status = "pending";

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
    this.orderTime = LocalDateTime.now();
  }

  public Order() {
    this.orderTime = LocalDateTime.now();
    this.itemList = new ArrayList<>();
  }

  public LocalDateTime getOrderTime() {
    return this.orderTime;
  }

  // If the Duration type isn't good then this needs to change to something else
  public Duration getTimeSinceOrder() {
    return Duration.between(this.orderTime, LocalDateTime.now());
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

  public void addItemToList(Item item) {
    this.itemList.add(item);
  }

  public int getTableNumber() {
    return tableNumber;
  }

  public void setTableNumber(int tableNumber) {
    this.tableNumber = tableNumber;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
