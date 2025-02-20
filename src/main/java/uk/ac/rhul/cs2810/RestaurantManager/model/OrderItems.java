package uk.ac.rhul.cs2810.RestaurantManager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ordersItems")
public class OrderItems {

  @OneToMany
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @OneToMany
  @JoinColumn(name = "item_id", nullable = false)
  private Item item;

  private int quantity;

  public OrderItems(Order order, Item item, int quantity) {
    this.order = order;
    this.item = item;
    this.quantity = quantity;
  }

  public OrderItems() {

  }

  public Order getOrder() {
    return this.order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Item getItem() {
    return this.item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public int getQuanitity() {
    return this.quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
