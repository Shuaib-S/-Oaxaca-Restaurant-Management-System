package uk.ac.rhul.cs2810.RestaurantManager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;

import uk.ac.rhul.cs2810.RestaurantManager.model.Order;
import uk.ac.rhul.cs2810.RestaurantManager.repository.OrderRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class OrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OrderRepository orderRepository;

  // Test 1
  @Test
  public void addOrderTest() throws JsonProcessingException, Exception {
    Order mockOrder = new Order(0, null, 0);
    String OrderMock = """
        "id": 1,
        "itemList": null,
        "tableNo": 0

        """;
    MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders.post("/api/orders")
            .contentType("application/json")
            .content(OrderMock))
        .andReturn();

    assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
  }
}
