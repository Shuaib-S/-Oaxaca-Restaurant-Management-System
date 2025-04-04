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
   * @param id The ID of a staff member.
   */
  public Login(int id, String username, String password) {
    this.id = id;
    this.username = username;
    this.password = password;
  }

  /**
   * Default constructor for Hibernate.
   */
  public Login() {

  }

  /**
   * Retrieves the ID of the login.
   * 
   * @return id The login ID.
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the ID of the login.
   * 
   * @param id The new login ID.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Retrieves the username of the staff member.
   * 
   * @return username The username of the staff member.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username of the staff member.
   * 
   * @param username The new username.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Retrieves the password of the staff member.
   * 
   * @return password The password.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Sets the password of the staff member.
   * 
   * @param password The new password.
   */
  public void setPassword(String password) {
    this.password = password;
  }

}
