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

/**
 * A class that represents an order in the restaurant system. It contains details about the order,
 * including the items ordered, the table number, the order time, status, confirmation, and payment
 * status.
 */
@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  int id;

  @OneToMany
  private List<Item> itemList;

  private int tableNumber;

  private LocalDateTime orderTime;

  private String status = "pending";

  private Boolean confirmed = false;

  private Boolean paid = false;

  /**
   * Constructor to create an Order with a given id, list of items, and table number. The order time
   * is automatically set to the current time.
   * 
   * @param id The Id for the order.
   * @param itemList The list of items that the order has.
   * @param tableNo The table number for the order (Required for UserStory)
   */
  public Order(int id, List<Item> itemList, int tableNo) {
    this.id = id;
    this.itemList = itemList;
    this.tableNumber = tableNo;
    this.orderTime = LocalDateTime.now();
  }

  /**
   * Default constructor for Hibernate. Initialises the order time to the current time and the item
   * list as an empty list.
   */
  public Order() {
    this.orderTime = LocalDateTime.now();
    this.itemList = new ArrayList<>();
  }

  /**
   * Gets the time when the order was placed.
   * 
   * @return The time the order was placed.
   */
  public LocalDateTime getOrderTime() {
    return this.orderTime;
  }

  /**
   * Calculates the time since the order was placed.
   * 
   * @return A Duration object representing the time since the order was placed.
   */
  public Duration getTimeSinceOrder() {
    return Duration.between(this.orderTime, LocalDateTime.now());
  }

  /**
   * Gets the id of the order.
   * 
   * @return id The ID of the order.
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the unique identifier for the order.
   * 
   * @param id The ID to set for the order.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the list of items in the order.
   * 
   * @return itemList A list of items in the order.
   */
  public List<Item> getItemList() {
    return itemList;
  }

  /**
   * Sets the list of items for the order.
   * 
   * @param itemList A list of items to be added to the order.
   */
  public void setItemList(List<Item> itemList) {
    this.itemList = itemList;
  }

  /**
   * Adds an item to the list of items in the order.
   * 
   * @param item The item to add to the order.
   */
  public void addItemToList(Item item) {
    this.itemList.add(item);
  }

  /**
   * Removes an item from the list of items in the order.
   * 
   * @param item The item to remove from the order.
   */
  public void removeItemFromList(Item item) {
    this.itemList.remove(item);
  }

  /**
   * Gets the table number of the order.
   * 
   * @return tableNumber The table number of the order.
   */
  public int getTableNumber() {
    return tableNumber;
  }

  /**
   * Sets the table number of the order.
   * 
   * @param tableNumber The table number to give the order.
   */
  public void setTableNumber(int tableNumber) {
    this.tableNumber = tableNumber;
  }

  /**
   * Gets the status of the order.
   * 
   * @return status The status of the order.
   */
  public String getStatus() {
    return status;
  }

  /**
   * Sets the status of the order.
   * 
   * @param status The status to set for the order.
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * Gets whether the order has been confirmed.
   * 
   * @return True if the order is confirmed, false otherwise.
   */
  public Boolean getConfirmed() {
    return confirmed;
  }

  /**
   * Sets whether the order has been confirmed.
   * 
   * @param confirmed True if the order is confirmed, false otherwise.
   */
  public void setConfirmed(Boolean confirmed) {
    this.confirmed = confirmed;
  }

  /**
   * Gets whether the order has been paid for.
   * 
   * @return True if the order has been paid for, false otherwise.
   */
  public Boolean getPaid() {
    return paid;
  }

  /**
   * Sets whether the order has been paid for.
   * 
   * @param paid True if the order has been paid for, false otherwise.
   */
  public void setPaid(Boolean paid) {
    this.paid = paid;
  }
}
