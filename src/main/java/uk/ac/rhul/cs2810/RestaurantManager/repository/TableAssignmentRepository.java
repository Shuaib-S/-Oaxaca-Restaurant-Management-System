package uk.ac.rhul.cs2810.RestaurantManager.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.ac.rhul.cs2810.RestaurantManager.model.TableAssignment;

/**
 * Repository interface for accessing and managing TableAssignment entities.
 */
@Repository
public interface TableAssignmentRepository extends JpaRepository<TableAssignment, Long> {

  /**
   * Finds all table assignments for a specific waiter.
   *
   * @param waiterUsername The username of the waiter.
   * @return A list of TableAssignment objects assigned to the given waiter.
   */
  List<TableAssignment> findByWaiterUsername(String waiterUsername);

  /**
   * Checks if a specific table number is already assigned to a waiter.
   *
   * @param tableNumber The number of the table.
   * @return true if the table is already assigned.
   */
  boolean existsByTableNumber(int tableNumber);
}
