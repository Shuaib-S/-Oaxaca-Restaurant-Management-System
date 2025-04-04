package uk.ac.rhul.cs2810.RestaurantManager.controller;

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
import com.fasterxml.jackson.databind.ObjectMapper;


import com.fasterxml.jackson.core.JsonProcessingException;

import uk.ac.rhul.cs2810.RestaurantManager.model.Order;
import uk.ac.rhul.cs2810.RestaurantManager.repository.OrderRepository;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class OrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OrderRepository orderRepository;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void addOrderWithInvalidInputTest() throws Exception {
    String invalidOrderJson = """
    {
      "id": 1,
      "itemList": null,
      "tableNumber": 0
    }
    """;

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/orders")
                    .contentType("application/json")
                    .content(invalidOrderJson))
            .andReturn();

    assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
  }

  @Test
    public void updateOrderStatusNotFoundTest() throws Exception {
      when(orderRepository.findById(99)).thenReturn(Optional.empty());

      String patchJson = """
        {
          "status": "completed"
        }
        """;

      MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/api/orders/99/status")
                      .contentType("application/json")
                      .content(patchJson))
              .andReturn();

      assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

}
