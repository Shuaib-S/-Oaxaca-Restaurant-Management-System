package uk.ac.rhul.cs2810.RestaurantManager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import uk.ac.rhul.cs2810.RestaurantManager.utils.StringListConverter;

/**
 * Represents a recipe in the restaurant system.
 */
@Entity
@Table(name = "recipe")
public class Recipe {

  /**
   * The id for the recipe.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long FID;

  /**
   * The name of the menu item associated with this recipe.
   */
  @Column(name = "item_name")
  private String itemName;

  /**
   * The list of ingredients used in the recipe.
   */
  @Column(name = "ingredients")
  @Convert(converter = StringListConverter.class)
  private List<String> ingredients;

  /**
   * Empty constructor for spring.
   */
  public Recipe() {}

  /**
   * Constructs a new recipe with the specified item name and list of ingredients.
   * 
   * @param itemName the name of the dish.
   * @param ingredients the ingredients of the dish.
   */
  public Recipe(String itemName, List<String> ingredients) {
    this.itemName = itemName;
    this.ingredients = ingredients;
  }

  /**
   * Returns the unique ID of the recipe.
   *
   * @return FID The recipe ID.
   */
  public Long getFID() {
    return FID;
  }

  /**
   * Sets the unique ID of the recipe.
   *
   * @param FID The recipe ID.
   */
  public void setFID(Long FID) {
    this.FID = FID;
  }

  /**
   * Returns the name of the menu item associated with this recipe.
   *
   * @return The item name.
   */
  public String getItemName() {
    return itemName;
  }

  /**
   * Sets the name of the menu item associated with this recipe.
   *
   * @param itemName The item name.
   */
  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  /**
   * Returns the list of ingredients for this recipe.
   *
   * @return ingredients A list of ingredients.
   */
  public List<String> getIngredients() {
    return ingredients;
  }

  /**
   * Sets the list of ingredients for this recipe.
   *
   * @param ingredients A list of ingredients.
   */
  public void setIngredients(List<String> ingredients) {
    this.ingredients = ingredients;
  }
}
