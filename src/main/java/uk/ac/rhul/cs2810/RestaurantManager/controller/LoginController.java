package uk.ac.rhul.cs2810.RestaurantManager.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import uk.ac.rhul.cs2810.RestaurantManager.repository.LoginRepository;
import uk.ac.rhul.cs2810.RestaurantManager.model.Login;

/**
 * A class that implements the controller needed for Login.
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {


  private final LoginRepository loginRepository;

  @Autowired
  public LoginController(LoginRepository loginRepository) {
    this.loginRepository = loginRepository;
  }

  /**
   * Response from the front end.
   * 
   * @param login Login sent from the frontend.
   * @return The response code.
   */
  @PostMapping
  public ResponseEntity<String> checkLogin(@RequestBody Login login) {
    BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
    String hashedPassword = bc.encode(login.getPassword());
    Login L = new Login();
    L.setPassword(hashedPassword);
    L.setUsername(login.getUsername());

    List<Login> logins = (List<Login>) loginRepository.findAll();

    for (Login loginEntry : logins) {
      if (loginEntry.getUsername().equals(login.getUsername())) {
        if (bc.matches(login.getPassword(), loginEntry.getPassword())) {
          return ResponseEntity.ok("http://localhost:8080/login-confirmation.html");
        }
      }
    }
    return null;
  }


}
