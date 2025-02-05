package uk.ac.rhul.cs2810.RestaurantManager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * A class that handles the name, description, price, calories and allergens of
 * each item on the
 * menu.
 */
@Entity
public class Item {
  @Id
  int id;

  private String title;
  private String description;
  private double price;
  private String category;
  private int calories;
  private String allergens;

  /**
   * 
   * Constructor that adds name, description, price, calories and allergens to an
   * Item object.
   * 
   * @param title       Name of the item.
   * @param description Description of the item.
   * @param price       Price of the item.
   * @param category    Category of items.
   * @param calories    Calories of the item.
   * @param allergens   Allergens found in the item.
   */
  public Item(int id, String title, String description, double price, String category,
      int calories, String allergens) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.price = price;
    this.category = category;
    this.calories = calories;
    this.allergens = allergens;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public int getCalories() {
    return calories;
  }

  public void setCalories(int calories) {
    this.calories = calories;
  }

  public String getAllergens() {
    return allergens;
  }

  public void setAllergens(String allergens) {
    this.allergens = allergens;
  }

}
