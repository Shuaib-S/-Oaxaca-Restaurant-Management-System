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

import uk.ac.rhul.cs2810.RestaurantManager.repository.OrderRepository;
import uk.ac.rhul.cs2810.RestaurantManager.repository.LoginRepository;
import uk.ac.rhul.cs2810.RestaurantManager.model.Order;
import uk.ac.rhul.cs2810.RestaurantManager.model.Item;


@RestController
@RequestMapping("/37.27.219.79:8080/api/login")
public class LoginController {


  private final LoginRepository loginRepository;

  @Autowired
  public LoginController(LoginRepository loginRepository) {
    this.loginRepository = loginRepository;
  }

  @PostMapping
  public ResponseEntity<Integer> checkLogin(@RequestBody Object login){
    System.out.println(login);
    return ResponseEntity.ok(1);
  }

}