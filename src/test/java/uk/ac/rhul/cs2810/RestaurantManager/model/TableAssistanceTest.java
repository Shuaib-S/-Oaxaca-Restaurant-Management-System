package uk.ac.rhul.cs2810.RestaurantManager.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TableAssistanceTest {

  private TableAssistance tableAssist = new TableAssistance(10, false);
  private TableAssistance tableAssist2 = new TableAssistance();

  // Test 1
  @Test
  public void getAndSetTest() {
    tableAssist2.setAssistance(true);
    tableAssist2.setTable(3);
    assertEquals(3, tableAssist2.getTable());
    assertEquals(true, tableAssist2.getAssistance());
    assertEquals(10, tableAssist.getTable());
    assertEquals(false, tableAssist.getAssistance());
  }
}
