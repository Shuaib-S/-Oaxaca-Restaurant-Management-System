package uk.ac.rhul.cs2810.RestaurantManager.model;

import jakarta.persistence.*;
import uk.ac.rhul.cs2810.RestaurantManager.utils.StringListConverter;

import java.util.List;

@Entity
@Table(name = "recipe")
public class Recipe {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long FID;

  @Column(name = "item_name")
  private String itemName;

  @Column(name = "ingredients")
  @Convert(converter = StringListConverter.class)
  private List<String> ingredients;

  // Constructors
  public Recipe() {}

  public Recipe(String itemName, List<String> ingredients) {
    this.itemName = itemName;
    this.ingredients = ingredients;
  }

  // Getters and Setters
  public Long getFID() {
    return FID;
  }

  public void setFID(Long FID) {
    this.FID = FID;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public List<String> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<String> ingredients) {
    this.ingredients = ingredients;
  }
}
