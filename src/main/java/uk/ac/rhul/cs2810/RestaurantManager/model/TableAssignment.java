package uk.ac.rhul.cs2810.RestaurantManager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "table_assignments")
public class TableAssignment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int tableNumber;

  @Column(nullable = false)
  private String waiterUsername;

  public TableAssignment() {
    // Default constructor
  }

  public TableAssignment(int tableNumber, String waiterUsername) {
    this.tableNumber = tableNumber;
    this.waiterUsername = waiterUsername;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getTableNumber() {
    return tableNumber;
  }

  public void setTableNumber(int tableNumber) {
    this.tableNumber = tableNumber;
  }

  public String getWaiterUsername() {
    return waiterUsername;
  }

  public void setWaiterUsername(String waiterUsername) {
    this.waiterUsername = waiterUsername;
  }
}
