package uk.ac.rhul.cs2810.RestaurantManager.model;

import java.util.List;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

//@Entity
//@Table(name = "orders") //Change this to the table name if not orders 
public class order {

  @Id
  int id;

  @OneToMany
  private List<Item> itemList;

  private int tableNumber;
  // Probably need a time stamp

  public order(int id, List<Item> itemList, int tableNo) {
    this.id = id;
    this.itemList = itemList;
    this.tableNumber = tableNo;
  }

  public order() {

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
