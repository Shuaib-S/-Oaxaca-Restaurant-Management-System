package uk.ac.rhul.cs2810.RestaurantManager.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.ac.rhul.cs2810.RestaurantManager.model.TableAssistance;

/**
 * Repository interface for managing TableAssistance entities.
 */
@Repository
public interface TableAssistanceRepository extends JpaRepository<TableAssistance, Long> {
  /**
   * Retrieves a list of assistance requests for a specific table number.
   *
   * @param tableNo The number of the table.
   * @return A list of TableAssistance entries for the specified table.
   */
  List<TableAssistance> findByTableNo(Integer tableNo);
}
