package uk.ac.rhul.cs2810.RestaurantManager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * A class that handles the name, description, price, calories and allergens of each item on the
 * menu.
 */
@Entity
@Table(name = "items")
public class Item {
  @Id
  int id;

  private String title;
  private String description;
  private double price;
  private String category;
  private int calories;
  private String allergens;
  private Boolean active = true;
  
  /**
   * Constructor that adds id, name, description, price, calories and allergens to an Item object.
   * 
   * @param id The ID of the item.
   * @param title Name of the item.
   * @param description Description of the item.
   * @param price Price of the item.
   * @param category Category of items.
   * @param calories Calories of the item.
   * @param allergens Allergens found in the item.
   */
  public Item(int id, String title, String description, double price, String category, int calories,
      String allergens) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.price = price;
    this.category = category;
    this.calories = calories;
    this.allergens = allergens;
    this.active = true;
  }

  /**
   * Default constructor required by Hibernate.
   */
  public Item() {
    // Default constructor required by Hibernate
  }

  /**
   * Gets the unique identifier of the item.
   *
   * @return id the item's ID.
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the unique identifier of the item.
   *
   * @param id the new ID to be assigned to the item.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets the name of the item.
   *
   * @return title the item's name.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the name of the item.
   *
   * @param title the new name of the item.
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the description of the item.
   *
   * @return description the item's description.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description of the item.
   *
   * @param description the new description of the item.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the price of the item.
   *
   * @return price the item's price.
   */
  public double getPrice() {
    return price;
  }

  /**
   * Sets the price of the item.
   *
   * @param price the new price of the item.
   */
  public void setPrice(double price) {
    this.price = price;
  }

  /**
   * Gets the category of the item.
   *
   * @return category the item's category.
   */
  public String getCategory() {
    return category;
  }

  /**
   * Sets the category of the item.
   *
   * @param category the new category of the item.
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * Gets the number of calories in the item.
   *
   * @return calories the calorie count of the item.
   */
  public int getCalories() {
    return calories;
  }

  /**
   * Sets the number of calories in the item.
   *
   * @param calories the new calorie count of the item.
   */
  public void setCalories(int calories) {
    this.calories = calories;
  }

  /**
   * Gets the allergens in the item.
   *
   * @return allergens the allergens in the item.
   */
  public String getAllergens() {
    return allergens;
  }

  /**
   * Sets the allergens in the item.
   *
   * @param allergens the new allergens for the item.
   */
  public void setAllergens(String allergens) {
    this.allergens = allergens;
  }

  /**
   * Checks whether the item is currently available on the menu.
   *
   * @return true if the item is available.
   */
  public boolean isActive() {
    return active;
  }

  /**
   * Sets the availability status of the item.
   *
   * @param active true to make the item available, false to make it unavailable.
   */
  public void setActive(boolean active) {
    this.active = active;
  }

}
