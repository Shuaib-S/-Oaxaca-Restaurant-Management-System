package uk.ac.rhul.cs2810.RestaurantManager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents an assistance request from a specific table in the restaurant.
 */
@Entity
@Table(name = "table_assistance")
public class TableAssistance {

  /**
   * The id for the assistance entry.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * The table number of the table requesting.
   */
  @Column(name = "table_no")
  private Integer tableNo;

  /**
   * Indicates whether assistance is currently needed, true means the table is requesting help.
   */
  private boolean help;

  /**
   * Default constructor required by spring.
   */
  public TableAssistance() {

  }

  /**
   * Constructs a TableAssistance object with the specified table number and help status.
   *
   * @param table The table number.
   * @param help Whether the table is requesting assistance.
   */
  public TableAssistance(Integer table, boolean help) {
    this.tableNo = table;
    this.help = help;
  }

  /**
   * Sets the table number associated with the assistance entry.
   *
   * @param table The table number.
   */
  public void setTable(Integer table) {
    this.tableNo = table;
  }

  /**
   * Gets the table number associated with the assistance entry.
   *
   * @return The table number.
   */
  public int getTable() {
    return this.tableNo;
  }

  /**
   * Sets the assistance status for the table.
   *
   * @param help true if assistance is needed; false otherwise.
   */
  public void setAssistance(boolean help) {
    this.help = help;
  }

  /**
   * Returns whether the table is currently requesting assistance.
   *
   * @return true if help is needed.
   */
  public boolean getAssistance() {
    return this.help;
  }

}