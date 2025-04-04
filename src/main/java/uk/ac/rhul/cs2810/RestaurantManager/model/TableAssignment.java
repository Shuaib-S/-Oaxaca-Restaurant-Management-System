package uk.ac.rhul.cs2810.RestaurantManager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents an assignment of a table to a specific waiter.
 */
@Entity
@Table(name = "table_assignments")
public class TableAssignment {

  /**
   * The id for the table assignment.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The number of the table assigned to a waiter.
   */
  private int tableNumber;

  /**
   * The username of the waiter assigned to the table.
   */
  @Column(nullable = false)
  private String waiterUsername;

  /**
   * Default constructor for spring.
   */
  public TableAssignment() {}

  /**
   * Constructs a TableAssignment with a table number and waiter username.
   *
   * @param tableNumber The table number of the table.
   * @param waiterUsername The username of the assigned waiter.
   */
  public TableAssignment(int tableNumber, String waiterUsername) {
    this.tableNumber = tableNumber;
    this.waiterUsername = waiterUsername;
  }

  /**
   * Gets the id of the assignment.
   *
   * @return The assignment id.
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the id of the assignment.
   *
   * @param id The assignment id.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the table number.
   *
   * @return The assigned table number.
   */
  public int getTableNumber() {
    return tableNumber;
  }

  /**
   * Sets the table number.
   *
   * @param tableNumber The table number to assign.
   */
  public void setTableNumber(int tableNumber) {
    this.tableNumber = tableNumber;
  }

  /**
   * Gets the username of the assigned waiter.
   *
   * @return The waiter’s username.
   */
  public String getWaiterUsername() {
    return waiterUsername;
  }

  /**
   * Sets the username of the waiter assigned to the table.
   *
   * @param waiterUsername The waiter’s username.
   */
  public void setWaiterUsername(String waiterUsername) {
    this.waiterUsername = waiterUsername;
  }
}
