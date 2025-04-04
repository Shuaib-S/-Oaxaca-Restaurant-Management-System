package uk.ac.rhul.cs2810.RestaurantManager.controller;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import uk.ac.rhul.cs2810.RestaurantManager.model.TableAssignment;
import uk.ac.rhul.cs2810.RestaurantManager.model.TableAssistance;
import uk.ac.rhul.cs2810.RestaurantManager.repository.TableAssignmentRepository;
import uk.ac.rhul.cs2810.RestaurantManager.repository.TableAssistanceRepository;
import uk.ac.rhul.cs2810.RestaurantManager.service.NotificationService;


public class TableAssignmentControllerTest {

    private TableAssignmentRepository tableAssignmentRepository;
    private TableAssistanceRepository tableAssistanceRepository;
    private TableAssignmentController controller;

    @BeforeEach
    public void setup() {
        tableAssignmentRepository = mock(TableAssignmentRepository.class);
        tableAssistanceRepository = mock(TableAssistanceRepository.class);
        controller = new TableAssignmentController(tableAssignmentRepository, tableAssistanceRepository);
    }

    @Test
    public void testAssignWaiter_NewAssignment_Success() {
        when(tableAssignmentRepository.existsByTableNumber(5)).thenReturn(false);

        ResponseEntity<?> response = controller.assignWaiter(5, "Alice");

        verify(tableAssignmentRepository, times(1)).save(any(TableAssignment.class));
        assertThat(response.getBody()).isEqualTo("Waiter assigned successfully!");
    }

    @Test
    public void testAssignWaiter_TableAlreadyAssigned() {
        when(tableAssignmentRepository.existsByTableNumber(5)).thenReturn(true);

        ResponseEntity<?> response = controller.assignWaiter(5, "Bob");

        assertThat(response.getBody()).isEqualTo("Table already assigned.");
    }

    @Test
    public void testGetAssignedTables_ByUsername() {
        List<TableAssignment> dummyAssignments = Arrays.asList(new TableAssignment(1, "Marcus"));
        when(tableAssignmentRepository.findByWaiterUsername("Marcus")).thenReturn(dummyAssignments);

        ResponseEntity<List<TableAssignment>> response = controller.getAssignedTables("Marcus");

        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getWaiterUsername()).isEqualTo("Marcus");
    }

    @Test
    public void testGetAllAssignedTables() {
        List<TableAssignment> all = Arrays.asList(
                new TableAssignment(1, "A"),
                new TableAssignment(2, "B")
        );
        when(tableAssignmentRepository.findAll()).thenReturn(all);

        ResponseEntity<List<TableAssignment>> response = controller.getAllAssignedTables();

        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    public void testUnassignWaiter_Success() {
        TableAssignment ta = new TableAssignment(5, "Alice");
        when(tableAssignmentRepository.findAll()).thenReturn(Collections.singletonList(ta));

        ResponseEntity<?> response = controller.unassignWaiter(5);

        verify(tableAssignmentRepository, times(1)).delete(ta);
        assertThat(response.getBody()).isEqualTo("Waiter unassigned successfully!");
    }

    @Test
    public void testUnassignWaiter_NotFound() {
        when(tableAssignmentRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = controller.unassignWaiter(99);

        assertThat(response.getBody()).isEqualTo("No waiter assigned to this table.");
    }

    @Test
    public void testSetAssistance() {
        ResponseEntity<?> response = controller.setAssistance(7);

        verify(tableAssistanceRepository, times(1)).save(any(TableAssistance.class));
        assertThat(response.getBody()).isEqualTo("Successuflly assisited someone");
    }

    @Test
    public void testGetAssistance() {
        List<TableAssistance> dummyList = Arrays.asList(new TableAssistance());
        when(tableAssistanceRepository.findAll()).thenReturn(dummyList);

        ResponseEntity<List<TableAssistance>> response = controller.getAssistance();

        assertThat(response.getBody()).hasSize(1);
    }

    @Test
    public void testRemoveAssistance() {
        TableAssistance entry = new TableAssistance();
        entry.setTable(10);
        when(tableAssistanceRepository.findAll()).thenReturn(Collections.singletonList(entry));

        Map<String, Integer> req = new HashMap<>();
        req.put("tableN", 10);
        ResponseEntity<?> response = controller.setAssistance(req);

        verify(tableAssistanceRepository, times(1)).delete(entry);
        assertThat(response.getBody()).isEqualTo("POGGERS");
    }
}
