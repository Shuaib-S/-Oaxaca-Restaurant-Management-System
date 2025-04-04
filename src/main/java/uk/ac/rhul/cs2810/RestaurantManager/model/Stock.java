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
  /**
   * The id for the stock item.
   */
  @Id
  int id;

  /**
   * The name of the stock item.
   */
  private String title;

  /**
   * The quantity of the stock item available.
   */
  private Integer quantity;

  /**
   * The category of the stock item.
   */
  private String category;

  /**
   * default constructor for spring.
   */
  public Stock() {}

  /**
   * 
   * Constructor that adds name, quantity and category to a stock object.
   * 
   * @param id The id of the item.
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

  /**
   * Returns the ID of the stock item.
   *
   * @return The stock item's ID.
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the ID of the stock item.
   *
   * @param id The stock item's new ID.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Returns the title of the stock item.
   *
   * @return The stock item's title.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the title of the stock item.
   *
   * @param title The new title of the stock item.
   */

  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Returns the category of the stock item.
   *
   * @return The stock item's category.
   */
  public String getCategory() {
    return category;
  }

  /**
   * Sets the category of the stock item.
   *
   * @param category The new category of the stock item.
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * Returns the quantity of the stock item.
   *
   * @return The stock item's quantity.
   */
  public Integer getQuantity() {
    return quantity;
  }

  /**
   * Sets the quantity of the stock item.
   *
   * @param quantity The new quantity of the stock item.
   */
  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
