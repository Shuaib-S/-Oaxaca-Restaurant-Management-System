package uk.ac.rhul.cs2810.RestaurantManager.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.rhul.cs2810.RestaurantManager.model.Notification;
import uk.ac.rhul.cs2810.RestaurantManager.repository.NotificationRepository;

/**
 * Service class for managing notifications in the restaurant management system. Provides methods
 * for creating, retrieving, and updating notifications for staff.
 */
@Service
public class NotificationService {
  @Autowired
  private NotificationRepository notificationRepository;

  public NotificationService(NotificationRepository notificationRepository) {
    this.notificationRepository = notificationRepository;
  }

  /**
   * Creates a new notification for a specific staff type. The notification is initially marked as
   * not done.
   *
   * @param staffType The type of staff the notification is created for.
   * @param message The content of the notification.
   * @param type The category of the notification.
   * @return The created notification.
   * @throws RuntimeException if an error occurs while creating the notification.
   */
  public Notification createNotification(String staffType, String message, String type){
    try{
      Notification notification = new Notification();
      notification.setStaffType(staffType);
      notification.setMessage(message);
      notification.setType(type);
      notification.setDone(false);

      return notificationRepository.save(notification);
    } catch (Exception e) {
      System.out.print("Error creating notification " + e.toString());
      throw new RuntimeException("Failed to create notification", e);
    }
  }

  /**
   * Retrieves all unread notifications for a specific staff type, ordered by creation time.
   * 
   * @param staffType The type of staff the notifications are fetched for.
   * @return A list of unread notifications for the specified staff type, ordered by the creation
   *         date.
   */
  public List<Notification> getStaffNotifications(String staffType) {
    return notificationRepository.findByStaffTypeAndIsDoneOrderByCreatedAtDesc(staffType, false);
  }

  /**
   * Retrieves the count of unread notifications for a specific staff type.
   * 
   * @param staffType The type of staff the notification count is fetched for.
   * @return The number of unread notifications for the specified staff type.
   */
  public long getNotifCount(String staffType) {
    return notificationRepository.countByStaffTypeAndIsDone(staffType, false);
  }

  /**
   * Marks a specific notification as read.
   * 
   * @param id The ID of the notification to mark as read.
   * @throws EntityNotFoundException if the given ID is not found.
   */
  public void markNotificationAsDone(Long id) {
    Notification notification = notificationRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Notification not found"));

    notification.setDone(true);
    notificationRepository.save(notification);
  }

}
