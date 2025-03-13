package uk.ac.rhul.cs2810.RestaurantManager.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.ac.rhul.cs2810.RestaurantManager.model.TableAssignment;

@Repository
public interface TableAssignmentRepository extends JpaRepository<TableAssignment, Long> {
  List<TableAssignment> findByWaiterUsername(String waiterUsername);

  boolean existsByTableNumber(int tableNumber);
}
