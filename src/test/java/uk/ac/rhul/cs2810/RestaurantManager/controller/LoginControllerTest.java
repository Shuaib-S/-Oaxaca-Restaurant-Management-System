package uk.ac.rhul.cs2810.RestaurantManager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import jakarta.transaction.Transactional;
import uk.ac.rhul.cs2810.RestaurantManager.repository.LoginRepository;
import uk.ac.rhul.cs2810.RestaurantManager.service.SessionService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class LoginControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private SessionService sessionService;

  @Autowired
  private LoginRepository loginRepository;

  @BeforeEach
  void beforeEach() {

  }

  // Test 1 checking for good login
  @Test
  @Transactional
  public void checkLoginTest() throws JsonProcessingException, Exception {
    String requestBody = """
            {
                "username": "test",
                "password": "test"
            }
        """;

    MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders.post("/api/login")
            .contentType("application/json")
            .content(requestBody))
        .andReturn();

    assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

    String jsonResponse = result.getResponse().getContentAsString();
    Map<String, Object> responseMap = new ObjectMapper().readValue(jsonResponse, new TypeReference<>() {
    });

    assertTrue((Boolean) responseMap.get("success"));
    assertEquals("/login-confirmation.html", responseMap.get("redirect"));
  }

  // Test 2 Checking for bad login
  @Test
  public void testLoginFailed() throws Exception {
    String requestBody = """
            {
                "username": "LeanardoDiCaprio",
                "password": "TitanicWasBad"
            }
        """;
    MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders.post("/api/login")
            .contentType("application/json")
            .content(requestBody))
        .andReturn();

    assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

    String jsonResponse = result.getResponse().getContentAsString();
    Map<String, Object> responseMap = new ObjectMapper().readValue(jsonResponse, new TypeReference<>() {
    });

    // Oposite of the otherone
    assertFalse((Boolean) responseMap.get("success"));
    assertEquals("Invalid username or password", responseMap.get("message"));

  }

  // Tests for validate, logout and getallwaiters required

}
