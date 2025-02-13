package uk.ac.rhul.cs2810.RestaurantManager;

import java.util.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestaurantManagerApplication {

  public static void main(String[] args) {
    // Request database credentials once
    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter Database Username: ");
    String username = scanner.nextLine();

    System.out.print("Enter Database Password: ");
    String password = scanner.nextLine();

    // Store credentials as system properties (so Spring Boot can use them)
    System.setProperty("DB_USERNAME", username);
    System.setProperty("DB_PASSWORD", password);

    // Start the application (ensuring credentials are set before Spring Boot initializes)
    SpringApplication.run(RestaurantManagerApplication.class, args);
  }

}
