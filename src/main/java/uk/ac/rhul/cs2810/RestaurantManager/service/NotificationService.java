package uk.ac.rhul.cs2810.RestaurantManager.service;

import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.message.ReusableSimpleMessage;
import org.apache.logging.log4j.message.SimpleMessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.rhul.cs2810.RestaurantManager.model.Notification;
import uk.ac.rhul.cs2810.RestaurantManager.repository.NotificationRepository;

import java.util.List;

@Service
public class NotificationService {
  @Autowired
  private NotificationRepository notificationRepository;

  public NotificationService(NotificationRepository notificationRepository) {
    this.notificationRepository = notificationRepository;
  }

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

  public List<Notification> getStaffNotifications(String staffType) {
    return notificationRepository.findByStaffTypeAndDoneIsOrderByCreatedAtDesc(staffType, false);
  }

  public long getNotifCount(String staffType) {
    return notificationRepository.countByStaffTypeAndDoneIs(staffType, false);
  }

  public void markNotificationAsRead(Integer id) {
    Notification notification = notificationRepository.findbyId(id)
        .orElseThrow(() -> new EntityNotFoundException("Notification not found"));

    notification.setDone(true);
    notificationRepository.save(notification);
  }

}
