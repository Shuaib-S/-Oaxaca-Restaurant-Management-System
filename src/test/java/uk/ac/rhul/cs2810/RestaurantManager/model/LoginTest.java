package uk.ac.rhul.cs2810.RestaurantManager.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LoginTest {

  private Login login1 = new Login();
  private Login login2 = new Login(1, "Marcus", "Haynes");

  // Test 1
  @Test
  public void loginTester() {
    login1.setId(2);
    login1.setPassword("Password");
    login1.setUsername("LeonardoDiCaprio");
    assertEquals(1, login2.getId());
    assertEquals("Marcus", login2.getUsername());
    assertEquals("Haynes", login2.getPassword());
    assertEquals(2, login1.getId());
    assertEquals("LeonardoDiCaprio", login1.getUsername());
    assertEquals("Password", login1.getPassword());
  }
}
