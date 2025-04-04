package uk.ac.rhul.cs2810.RestaurantManager.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.rhul.cs2810.RestaurantManager.model.TableAssignment;
import uk.ac.rhul.cs2810.RestaurantManager.model.TableAssistance;
import uk.ac.rhul.cs2810.RestaurantManager.repository.TableAssignmentRepository;
import uk.ac.rhul.cs2810.RestaurantManager.repository.TableAssistanceRepository;
import uk.ac.rhul.cs2810.RestaurantManager.service.NotificationService;

/**
 * Controller for managing table assignments and assistance requests.
 */
@RestController
@RequestMapping("/api/tableAssignments")
public class TableAssignmentController {

  @Autowired
  private TableAssignmentRepository tableAssignmentRepository;

  @Autowired
  private TableAssistanceRepository tableAssistanceRepositry;

  @Autowired
  private NotificationService notificationService;

  /**
   * Default Constructor.
   */
  public TableAssignmentController() {}

  /**
   * Assigns a waiter to a specific table.
   *
   * @param tableNumber The table number to assign.
   * @param waiterUsername The username of the waiter to assign.
   * @return A success message or an error if the table is already assigned.
   */
  @PostMapping("/assign")
  public ResponseEntity<?> assignWaiter(@RequestParam int tableNumber,
      @RequestParam String waiterUsername) {
    if (tableAssignmentRepository.existsByTableNumber(tableNumber)) {
      return ResponseEntity.badRequest().body("Table already assigned.");
    }
    TableAssignment assignment = new TableAssignment();
    assignment.setTableNumber(tableNumber);
    assignment.setWaiterUsername(waiterUsername);
    tableAssignmentRepository.save(assignment);
    return ResponseEntity.ok("Waiter assigned successfully!");
  }

  /**
   * Retrieves all tables assigned to a specific waiter.
   *
   * @param waiterUsername The username of the waiter.
   * @return A list of assigned tables for the given waiter.
   */
  @GetMapping("/assignedTables/{waiterUsername}")
  public ResponseEntity<List<TableAssignment>> getAssignedTables(
      @PathVariable String waiterUsername) {
    return ResponseEntity.ok(tableAssignmentRepository.findByWaiterUsername(waiterUsername));
  }

  /**
   * Retrieves all current table assignments in the system.
   *
   * @return A list of all table assignments.
   */
  @GetMapping("/assignedTables")
  public ResponseEntity<List<TableAssignment>> getAllAssignedTables() {
    return ResponseEntity.ok(tableAssignmentRepository.findAll());
  }

  /**
   * Unassigns a waiter from a specific table.
   *
   * @param tableNumber The table number to unassign.
   * @return A success message or an error if no waiter is assigned to the table.
   */
  @PostMapping("/unassign")
  public ResponseEntity<?> unassignWaiter(@RequestParam int tableNumber) {
    List<TableAssignment> assignments = tableAssignmentRepository.findAll();

    TableAssignment assignmentToRemove = assignments.stream()
        .filter(a -> a.getTableNumber() == tableNumber).findFirst().orElse(null);

    if (assignmentToRemove == null) {
      return ResponseEntity.badRequest().body("No waiter assigned to this table.");
    }

    tableAssignmentRepository.delete(assignmentToRemove);
    return ResponseEntity.ok("Waiter unassigned successfully!");
  }

  /**
   * Marks a table as requesting assistance and triggers a notification to waiters.
   *
   * @param tableNumber The table requesting assistance.
   * @return A success message confirming the assistance request.
   */
  @PostMapping("/assistanceSet")
  public ResponseEntity<?> setAssistance(@RequestParam Integer tableNumber) {
    TableAssistance tableAssist = new TableAssistance();
    tableAssist.setTable(tableNumber);
    tableAssist.setAssistance(true);
    tableAssistanceRepositry.save(tableAssist);

    notificationService.createNotification(
        "waiter",
        "Table " + tableNumber + " needs assistance",
        "table_assistance"
    );

    return ResponseEntity.ok("Successuflly assisited someone");
  }

  /**
   * Retrieves all current assistance requests.
   *
   * @return A list of tables currently requesting assistance.
   */
  @GetMapping("/assistance")
  public ResponseEntity<List<TableAssistance>> getAssistance() {
    return ResponseEntity.ok(tableAssistanceRepositry.findAll());
  }

  /**
   * Removes an active assistance request from a table.
   *
   * @param assistanceN A map containing the key tableN (the table number).
   * @return A success message confirming removal.
   */
  @PostMapping("/removeAssistance")
  public ResponseEntity<?> setAssistance(@RequestBody Map<String, Integer> assistanceN) {
    Integer tableNumberToRemove = assistanceN.get("tableN");
    List<TableAssistance> wooo = this.tableAssistanceRepositry.findAll();
    for (TableAssistance entry : wooo) {
      if (entry.getTable() == tableNumberToRemove) {
        this.tableAssistanceRepositry.delete(entry);
      }
    }
    return ResponseEntity.ok("Sucessfully Removed Assistance");
  }

}
