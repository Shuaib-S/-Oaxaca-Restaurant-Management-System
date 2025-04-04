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
import uk.ac.rhul.cs2810.RestaurantManager.model.Notification;
import uk.ac.rhul.cs2810.RestaurantManager.service.NotificationService;

/**
 * Controller that handles notifications for restaurant staff.
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
  @Autowired
  private NotificationService notificationService;

  /**
   * Default constructor for spring
   */
  public NotificationController() {}

  /**
   * Retrieves a list of notifications for a specific staff type.
   *
   * @param staffType The type of staff to filter notifications.
   * @return A ResponseEntity containing a list of notifications for the specified staff type.
   *         Returns 400 (Bad Request) if staffType is null or empty.
   */
  @GetMapping("/get")
  public ResponseEntity<List<Notification>> getStaffNotifications(
      @RequestParam("staffType") String staffType
  ) {

    if (staffType == null || staffType.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    List<Notification> notifications = notificationService.getStaffNotifications(staffType);
    return ResponseEntity.ok(notifications);
  }

  /**
   * Retrieves the count of unread notifications for a specific staff type.
   *
   * @param staffType The type of staff to count notifications for.
   * @return A ResponseEntity containing the number of unread notifications for the specified staff
   *         type.
   */
  @GetMapping("/count")
  public ResponseEntity<Long> getNotifCount (String staffType) {
    long count = notificationService.getNotifCount(staffType);
    return ResponseEntity.ok(count);
  }

  /**
   * Marks a specific notification as done.
   *
   * @param id The ID of the notification to mark as done.
   * @return A ResponseEntity indicating that the notification has been marked as done.
   */
  @PostMapping("/{id}/read")
  public ResponseEntity<Void> markNotificationAsDone(@PathVariable Long id) {
    notificationService.markNotificationAsDone(id);
    return ResponseEntity.ok().build();
  }
}
