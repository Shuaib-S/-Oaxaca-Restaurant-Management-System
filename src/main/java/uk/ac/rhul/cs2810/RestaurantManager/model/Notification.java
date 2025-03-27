package uk.ac.rhul.cs2810.RestaurantManager.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

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

  public Notification() {
    this.createdAt = LocalDateTime.now();
  }

  public long getId() {
    return id;
  }

  public String getStaffType() {
    return staffType;
  }

  public void setStaffType(String staffType) {
    this.staffType = staffType;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isDone() {
    return isDone;
  }

  public void setDone(boolean done) {
    isDone = done;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
