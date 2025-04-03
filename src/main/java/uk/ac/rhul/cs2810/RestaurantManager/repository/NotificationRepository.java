package uk.ac.rhul.cs2810.RestaurantManager.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.ac.rhul.cs2810.RestaurantManager.model.Notification;

/**
 * Repository interface for managing notification entities in the database. Provides methods for
 * querying notifications based on staff type and read status.
 */
@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

  /**
   * Retrieves a list of notifications for a specific staff type and read status. The notifications
   * are ordered in descending order.
   *
   * @param staffType The type of staff to filter notifications by.
   * @param isDone The read status of the notifications, true if read.
   * @return A list of notifications matching the specified criteria.
   */
  List<Notification> findByStaffTypeAndIsDoneOrderByCreatedAtDesc(String staffType, boolean isDone);

  /**
   * Retrieves the number of notifications for a specific staff type and read status.
   *
   * @param staffType The type of staff to count notifications for.
   * @param isDone The read status of the notifications, true if read.
   * @return The number of notifications matching the specified criteria.
   */
  long countByStaffTypeAndIsDone(String staffType, boolean isDone);
}
