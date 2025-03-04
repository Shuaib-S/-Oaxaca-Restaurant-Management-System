package uk.ac.rhul.cs2810.RestaurantManager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import uk.ac.rhul.cs2810.RestaurantManager.repository.LoginRepository;
import uk.ac.rhul.cs2810.RestaurantManager.model.Login;


@RestController
@RequestMapping("/37.27.219.79:8080/api/login")
public class LoginController {


  private final LoginRepository loginRepository;

  @Autowired
  public LoginController(LoginRepository loginRepository) {
    this.loginRepository = loginRepository;
  }

  @PostMapping
  public ResponseEntity<Login> checkLogin(@RequestBody Login login) {
    BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
    String username = bc.encode(login.getUsername());
    String password = bc.encode(login.getPassword());
    return ResponseEntity.ok(login);
  }

}
