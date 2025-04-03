package uk.ac.rhul.cs2810.RestaurantManager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import uk.ac.rhul.cs2810.RestaurantManager.model.Login;
import uk.ac.rhul.cs2810.RestaurantManager.repository.LoginRepository;
import uk.ac.rhul.cs2810.RestaurantManager.service.SessionService;

/**
 * A class that implements the controller needed for Login.
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {

  private final LoginRepository loginRepository;
  private final SessionService sessionService;

  /**
   * Constructs a new LoginController with the necessary dependencies.
   *
   * @param loginRepository The repository for accessing login data.
   * @param sessionService The service for handling session management.
   * @return A ResponseEntity containing authentication success status and redirection details.
   */
  @Autowired
  public LoginController(LoginRepository loginRepository, SessionService sessionService) {
    this.loginRepository = loginRepository;
    this.sessionService = sessionService;
  }

  /**
   * Response from the front end.
   * 
   * @param login Login sent from the frontend.
   * @return The response code.
   */
  @PostMapping
  public ResponseEntity<Map<String, Object>> checkLogin(@RequestBody Login login,
      HttpServletResponse response) {

    BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
    String hashedPassword = bc.encode(login.getPassword());
    Login L = new Login();
    L.setPassword(hashedPassword);
    L.setUsername(login.getUsername());

    List<Login> logins = (List<Login>) loginRepository.findAll();

    Map<String, Object> responseBody = new HashMap<>();

    for (Login loginEntry : logins) {
      if (loginEntry.getUsername().equals(login.getUsername())) {
        if (bc.matches(login.getPassword(), loginEntry.getPassword())) {

          String sessionId = sessionService.createSession(L.getUsername());

          Cookie sessionCookie = new Cookie("RESTAURANT_SESSION", sessionId);
          sessionCookie.setHttpOnly(true);
          sessionCookie.setPath("/");
          sessionCookie.setMaxAge(43200);
          response.addCookie(sessionCookie);

          responseBody.put("success", true);
          responseBody.put("redirect", "/login-confirmation.html");

          return ResponseEntity.ok(responseBody);
        }
      }
    }
    responseBody.put("success", false);
    responseBody.put("message", "Invalid username or password");

    return ResponseEntity.ok(responseBody);
  }

  /**
   * Validates whether a user session is active.
   *
   * @param request The HTTP request containing session cookies.
   * @return A ResponseEntity containing authentication status and username if valid.
   */
  @GetMapping("/validate")
  public ResponseEntity<Map<String, Object>> validateSession(HttpServletRequest request) {
    Map<String, Object> responseBody = new HashMap<>();

    String sessionId = null;
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("RESTAURANT_SESSION".equals(cookie.getName())) {
          sessionId = cookie.getValue();
          break;
        }
      }
    }

    if (sessionId != null && sessionService.isSessionValid(sessionId)) {
      String username = sessionService.getUsernameForSession(sessionId);
      responseBody.put("authenticated", true);
      responseBody.put("username", username);
      return ResponseEntity.ok(responseBody);
    }

    responseBody.put("authenticated", false);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
  }

  /**
   * Retrieves a list of all waiters (staff members) in the system.
   *
   * @return A ResponseEntity containing a list of usernames.
   */
  @GetMapping("/all")
  public ResponseEntity<List<String>> getAllWaiters() {
    List<Login> logins = (List<Login>) loginRepository.findAll();
    List<String> waiters = logins.stream().map(Login::getUsername).toList();
    return ResponseEntity.ok(waiters);
  }

  /**
   * Logs out a user by invalidating the session and clearing the session cookie.
   *
   * @param request The HTTP request containing the session cookie.
   * @param response The HTTP response where the session cookie is cleared.
   * @return A ResponseEntity confirming the logout action.
   */
  @PostMapping("/logout")
  public ResponseEntity<Map<String, Object>> logout(HttpServletRequest request,
      HttpServletResponse response) {
    Map<String, Object> responseBody = new HashMap<>();

    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("RESTAURANT_SESSION".equals(cookie.getName())) {
          sessionService.invalidateSession(cookie.getValue());

          Cookie clearCookie = new Cookie("RESTAURANT_SESSION", "");
          clearCookie.setMaxAge(0);
          clearCookie.setPath("/");
          response.addCookie(clearCookie);

          break;
        }
      }
    }

    responseBody.put("success", true);
    return ResponseEntity.ok(responseBody);
  }
}
