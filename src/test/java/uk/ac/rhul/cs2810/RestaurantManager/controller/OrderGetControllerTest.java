package uk.ac.rhul.cs2810.RestaurantManager.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import jakarta.transaction.Transactional;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import uk.ac.rhul.cs2810.RestaurantManager.model.Order;

import uk.ac.rhul.cs2810.RestaurantManager.model.Item;
import uk.ac.rhul.cs2810.RestaurantManager.repository.ItemRepository;
import uk.ac.rhul.cs2810.RestaurantManager.repository.OrderRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class OrderGetControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ItemRepository itemRepository;

  @BeforeEach
  void beforeEach() {

  }

  // Test 1
  @Test
  public void getOrderTest() throws JsonProcessingException, Exception {
    MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders.get("/api/CurrentOrders/all").contentType("application/json")).andReturn();
    assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    List<Order> orders = objectMapper.readValue(result.getResponse().getContentAsString(),
        new TypeReference<List<Order>>() {
        });
    /**
     * Because there are not static orders in the database we I can't use a
     * generalised order, as the first orders id changes
     * I can call findbyId to find that order and then check it pulled from the
     * endpoint.
     */
    Order gottenOrder = orders.get(0);
    Order o = orderRepository.findById(gottenOrder.getId()).orElse(null);
    assertEquals(o.getId(), gottenOrder.getId());
    assertEquals(o.getTableNumber(), gottenOrder.getTableNumber());
  }

  // Test 2
  @Test
  public void getItemNamesTest() throws JsonProcessingException, Exception {
    MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders.get("/api/CurrentOrders/orderItems").contentType("application/json"))
        .andReturn();
    assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    List<Order> orders = objectMapper.readValue(result.getResponse().getContentAsString(),
        new TypeReference<List<Order>>() {
        });
    Order gottenOrder = orders.get(0);
    Order o = orderRepository.findById(gottenOrder.getId()).orElse(null);
    assertEquals(o.getId(), gottenOrder.getId());
  }

  // Test 3
  @Test
  public void addItemTest() throws JsonProcessingException, Exception {
    String itemTest = """
            {
                "orderID": 1,
                "itemName": "Taco Tuesday"
            }
        """;

    MvcResult result = mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/CurrentOrders/addItem").contentType("application/json").content(itemTest))
        .andReturn();

    assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    String jsonResponse = result.getResponse().getContentAsString();
    assertTrue(jsonResponse.contains("addItem successful."));
    System.out.println(jsonResponse);
  }

  // Test 4
  @Test
  public void getOrderByTableTest() throws JsonProcessingException, Exception {
    int tableId = 5;
    MvcResult result = mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/CurrentOrders/table/{tableId}", tableId).contentType("application/json"))
        .andReturn();
    assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

  }

  // Test 5 (Tests for noItem to remove)
  @Test
  public void removeItemTest_NoItem() throws JsonProcessingException, Exception {
    String itemTest = """
            {
                "orderID": 1,
                "itemName": "Taco Tuesday"
            }
        """;
    MvcResult result = mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/CurrentOrders/removeItem").contentType("application/json")
                .content(itemTest))
        .andReturn();

    assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    String jsonResponse = result.getResponse().getContentAsString();
    assertTrue(jsonResponse.contains("Item is not in this order, it is not removed."));
  }

  // Test 6 (Tests if the item is there)
  @Test
  @Transactional
  public void removeItemTest_Item() throws JsonProcessingException, Exception {
    String itemTest = "";

    Iterable<Order> g = this.orderRepository.findAll();
    if (g.iterator().hasNext()) {
      Order OrdertoTest = g.iterator().next();
      int orderID = OrdertoTest.getId();
      List<Item> i = OrdertoTest.getItemList();
      String itemName = i.get(0).getTitle();

      itemTest = """
          {
              "orderID": %d,
              "itemName": "%s"
          }
          """.formatted(orderID, itemName);
    }

    MvcResult result = mockMvc
        .perform(MockMvcRequestBuilders.post("/api/CurrentOrders/removeItem")
            .contentType("application/json")
            .content(itemTest))
        .andReturn();

    assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    String jsonResponse = result.getResponse().getContentAsString();
    assertTrue(jsonResponse.contains("Item removed from order."));

  }

  // Test 7 Confirmed
  @Test
  @Transactional
  public void confirmOrderTest() throws JsonProcessingException, Exception {
    String itemTest = "";

    Iterable<Order> g = this.orderRepository.findAll();
    if (g.iterator().hasNext()) {
      Order OrdertoTest = g.iterator().next();
      int orderID = OrdertoTest.getId();

      itemTest = """
          {
              "orderID": %d
          }
          """.formatted(orderID);
    }

    MvcResult result = mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/CurrentOrders/confirmOrder").contentType("application/json")
                .content(itemTest))
        .andReturn();

    assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    String jsonResponse = result.getResponse().getContentAsString();
    assertTrue(jsonResponse.contains("Order confirmed."));
    System.out.println(jsonResponse);
  }

  // Test 8 Not confirmed
  @Test
  public void notConfirmedTest() throws JsonProcessingException, Exception {
    String itemTest = """
            {
                "orderID": null
            }
        """;
    MvcResult result = mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/CurrentOrders/confirmOrder").contentType("application/json")
                .content(itemTest))
        .andReturn();

    assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    String jsonResponse = result.getResponse().getContentAsString();
    assertTrue(jsonResponse.contains("Order not confirmed."));
  }

  /**
   * 99% Test coverage of this class,
   * The only branch not tested is a single if statement in removeItem
   */

}
