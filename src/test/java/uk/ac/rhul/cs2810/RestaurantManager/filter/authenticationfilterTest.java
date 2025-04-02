package uk.ac.rhul.cs2810.RestaurantManager.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import jakarta.servlet.http.Cookie;
import uk.ac.rhul.cs2810.RestaurantManager.service.SessionService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class authenticationfilterTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SessionService sessionService;

  @Test
  void testUnprotectedResourceBypassesFilter() throws Exception {
    MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders.get("/waiters-login.html"))
        .andReturn();

    assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
  }

  @Test
  void TestFound() throws Exception {
    MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders.get("/protected/dashboard")
            .cookie(new Cookie("RESTAURANT_SESSION", "valid-session-id")))
        .andReturn();
    assertEquals(HttpStatus.FOUND.value(), result.getResponse().getStatus());
  }
}
