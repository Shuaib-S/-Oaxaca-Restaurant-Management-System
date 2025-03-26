package uk.ac.rhul.cs2810.RestaurantManager.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {
  @Id
  @GeneratedValue
  private long id;

  @Column(name = "message")
  private String message;

  @Column(name = "type")
  private String type;

  @Column(name = "is_done")
  private boolean isDone = false;

  @Column(name = "time_created")
  private LocalDateTime createdAt;

  public Notification() {
    this.createdAt = LocalDateTime.now();
  }
}
