package uk.ac.rhul.cs2810.RestaurantManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Beginning of the Springboot Application restaurant manager.
 */
@SpringBootApplication
public class RestaurantManagerApplication {

  /**
   * Default constructor for spring.
   */
  public RestaurantManagerApplication() {}

  /**
   * Main method used to launch the application.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(RestaurantManagerApplication.class, args);
  }

}
