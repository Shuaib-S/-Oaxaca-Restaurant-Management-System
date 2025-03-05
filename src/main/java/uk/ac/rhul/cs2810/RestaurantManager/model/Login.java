package uk.ac.rhul.cs2810.RestaurantManager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * A class that handles the logins of staff members in the restaurant.
 */
@Entity
@Table(name = "logins")
public class Login {
  @Id
  @GeneratedValue
  int id;

  private String username;
  private String password;

  /**
   * 
   * Constructor that adds the id, username and password of the login.
   * 
   * @param username The username of a staff member.
   * @param password The password of a staff member.
   */
  public Login(int id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  public Login() {

  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
