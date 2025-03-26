package uk.ac.rhul.cs2810.RestaurantManager.controller;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.ac.rhul.cs2810.RestaurantManager.model.Notification;
import uk.ac.rhul.cs2810.RestaurantManager.service.NotificationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
  @Autowired
  private NotificationService notificationService;

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

  @GetMapping("/count")
  public ResponseEntity<Long> getNotifCount (String staffType) {
    long count = notificationService.getNotifCount(staffType);
    return ResponseEntity.ok(count);
  }

  @PostMapping("/{id}/read")
  public ResponseEntity<Void> markNotificationAsDone(@PathVariable Long id) {
    notificationService.markNotificationAsDone(id);
    return ResponseEntity.ok().build();
  }
}
