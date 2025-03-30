package uk.ac.rhul.cs2810.RestaurantManager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * A class that handles the name, quantity and category of Stock.
 */
@Entity
@Table(name = "stock")
public class Stock {
  @Id
  int id;

  private String title;
  private Integer quantity;
  private String category;

  public Stock() {}

  /**
   * 
   * Constructor that adds name, quantity and category to a stock object.
   * 
   * @param title Name of the item.
   * @param category Category of items.
   * @param quantity The amount in stock of an ingredient.
   */
  public Stock(int id, String title, Integer quantity, String category) {
    this.id = id;
    this.title = title;
    this.quantity = quantity;
    this.category = category;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
