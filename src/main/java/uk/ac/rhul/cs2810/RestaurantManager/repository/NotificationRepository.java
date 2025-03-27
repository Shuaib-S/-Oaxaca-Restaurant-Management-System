package uk.ac.rhul.cs2810.RestaurantManager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.ac.rhul.cs2810.RestaurantManager.model.Notification;

import java.util.List;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {
  List<Notification> findByStaffTypeAndIsDoneOrderByCreatedAtDesc(String staffType, boolean isDone);

  long countByStaffTypeAndIsDone(String staffType, boolean isDone);
}
