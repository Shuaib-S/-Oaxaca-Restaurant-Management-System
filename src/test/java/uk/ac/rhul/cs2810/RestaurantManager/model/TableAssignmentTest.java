package uk.ac.rhul.cs2810.RestaurantManager.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TableAssignmentTest {

  private TableAssignment tableassign = new TableAssignment(10, "Marcus");
  private TableAssignment tableassign2 = new TableAssignment();

  // Test 1
  @Test
  public void getAndSetTest() {
    tableassign2.setId(1L);
    tableassign2.setTableNumber(4);
    tableassign2.setWaiterUsername("Gabe");
    assertEquals(10, tableassign.getTableNumber());
    assertEquals("Marcus", tableassign.getWaiterUsername());
    assertEquals(1L, tableassign2.getId());
    assertEquals(4, tableassign2.getTableNumber());
    assertEquals("Gabe", tableassign2.getWaiterUsername());

  }
}
