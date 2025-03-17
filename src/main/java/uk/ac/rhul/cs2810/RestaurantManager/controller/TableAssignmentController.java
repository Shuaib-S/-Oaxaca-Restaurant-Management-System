package uk.ac.rhul.cs2810.RestaurantManager.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.rhul.cs2810.RestaurantManager.model.TableAssignment;
import uk.ac.rhul.cs2810.RestaurantManager.model.TableAssistance;
import uk.ac.rhul.cs2810.RestaurantManager.repository.TableAssignmentRepository;
import uk.ac.rhul.cs2810.RestaurantManager.repository.TableAssistanceRepository;

@RestController
@RequestMapping("/api/tableAssignments")
public class TableAssignmentController {

  @Autowired
  private TableAssignmentRepository tableAssignmentRepository;

  @Autowired
  private TableAssistanceRepository tableAssistanceRepositry;

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

  @GetMapping("/assignedTables/{waiterUsername}")
  public ResponseEntity<List<TableAssignment>> getAssignedTables(
      @PathVariable String waiterUsername) {
    return ResponseEntity.ok(tableAssignmentRepository.findByWaiterUsername(waiterUsername));
  }

  @GetMapping("/assignedTables")
  public ResponseEntity<List<TableAssignment>> getAllAssignedTables() {
    return ResponseEntity.ok(tableAssignmentRepository.findAll());
  }

  // The Assistance Button assigning to tables stuff
  @PostMapping("/assistanceSet")
  public ResponseEntity<?> setAssistance(@RequestParam int tableNumber, @RequestParam boolean assistance) {
    TableAssistance tableAssist = new TableAssistance();
    tableAssist.setTable(tableNumber);
    tableAssist.setAssistance(assistance);
    tableAssistanceRepositry.save(tableAssist);
    return ResponseEntity.ok("Successuflly assisited someone");
  }

  @GetMapping("/assistance")
  public ResponseEntity<List<TableAssistance>> getAssistance() {
    return ResponseEntity.ok(tableAssistanceRepositry.findAll());
  }

}
