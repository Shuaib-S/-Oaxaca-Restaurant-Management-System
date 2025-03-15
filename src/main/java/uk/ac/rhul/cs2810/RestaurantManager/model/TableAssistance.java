package uk.ac.rhul.cs2810.RestaurantManager.model;

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

  private int table;

  /**
  True is they need help
  False is they no need help
  */
  private boolean help;

  public TableAssistance(){

  }

  public TableAssistance(int table, boolean help){
    this.table = table;
    this.help = help;
  }

  public void setTable(int table){
    this.table = table;
  }
  public int getTable(){
    return this.table;
  }

  public void setAssistance(boolean help){
    this.help = help;
  }

  public boolean getAssistance(){
    return this.help;
  }

}