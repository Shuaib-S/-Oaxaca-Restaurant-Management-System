package uk.ac.rhul.cs2810.RestaurantManager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

/**
 * Represents a notification entity used for communicating messages to staff members. Each
 * notification has a type, message, staff type designation, and a status indicating if it's done.
 */
@Entity
@Table(name = "notifications")
public class Notification {
  @Id
  @GeneratedValue
  private long id;

  @Column(name = "staff_type")
  private String staffType;

  @Column(name = "message")
  private String message;

  @Column(name = "type")
  private String type;

  @Column(name = "is_done")
  private boolean isDone = false;

  @Column(name = "time_created")
  @CreationTimestamp
  private LocalDateTime createdAt;

  /**
   * Default constructor that initialises the notification's creation time stamp.
   */
  public Notification() {
    this.createdAt = LocalDateTime.now();
  }

  /**
   * Retrieves the unique identifier of the notification.
   *
   * @return The ID of the notification.
   */
  public long getId() {
    return id;
  }

  /**
   * Retrieves the type of staff that the notification is intended for.
   *
   * @return staffType The staff type.
   */
  public String getStaffType() {
    return staffType;
  }

  /**
   * Sets the type of staff the notification is intended for.
   *
   * @param staffType The staff type.
   */
  public void setStaffType(String staffType) {
    this.staffType = staffType;
  }

  /**
   * Retrieves the message content of the notification.
   *
   * @return message The notification message.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets the message content of the notification.
   *
   * @param message The notification message.
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Retrieves the type of the notification.
   *
   * @return type The type of notification.
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the type of the notification.
   *
   * @param type The type of notification.
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Checks if the notification has been marked as done.
   *
   * @return true if the notification is completed, otherwise false.
   */
  public boolean isDone() {
    return isDone;
  }

  /**
   * Marks the notification as done or not done.
   *
   * @param done true to mark the notification as completed, otherwise false.
   */
  public void setDone(boolean done) {
    isDone = done;
  }

  /**
   * Retrieves the time stamp when the notification was created.
   *
   * @return The creation time stamp of the notification.
   */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
