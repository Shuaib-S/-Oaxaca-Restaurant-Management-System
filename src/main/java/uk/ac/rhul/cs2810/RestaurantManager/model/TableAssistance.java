package uk.ac.rhul.cs2810.RestaurantManager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "table_assistance")
public class TableAssistance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "table_no")
  private Integer tableNo;

  /**
   * True is they need help
   * False is they no need help
   */
  private boolean help;

  public TableAssistance() {

  }

  public TableAssistance(Integer table, boolean help) {
    this.tableNo = table;
    this.help = help;
  }

  public void setTable(Integer table) {
    this.tableNo = table;
  }

  public int getTable() {
    return this.tableNo;
  }

  public void setAssistance(boolean help) {
    this.help = help;
  }

  public boolean getAssistance() {
    return this.help;
  }

}