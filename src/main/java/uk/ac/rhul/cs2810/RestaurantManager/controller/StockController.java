package uk.ac.rhul.cs2810.RestaurantManager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.rhul.cs2810.RestaurantManager.model.Stock;
import uk.ac.rhul.cs2810.RestaurantManager.repository.StockRepository;

/**
 * Controller for handling API endpoints related to stock management.
 */
@RestController
@RequestMapping("/api/stock")
public class StockController {

  private final StockRepository stockRepository;

  /**
   * Constructs a StockController with the given StockRepository.
   *
   * @param stockRepository The repository used to access and update stock data.
   */
  @Autowired
  public StockController(StockRepository stockRepository) {
    this.stockRepository = stockRepository;
  }

  /**
   * Retrieves a list of all stock items in the system.
   *
   * @return A ResponseEntity containing a list of stock items represented as maps with fields: id,
   *         title, quantity, and category.
   */
  @GetMapping
  public ResponseEntity<List<Map<String, Object>>> getStock() {
    Iterable<Stock> stockItems = stockRepository.findAll();
    List<Map<String, Object>> stockList = new ArrayList<>();
    for (Stock stock : stockItems) {
      Map<String, Object> stockMap = new HashMap<>();
      stockMap.put("id", stock.getId());
      stockMap.put("title", stock.getTitle());
      stockMap.put("quantity", stock.getQuantity());
      stockMap.put("category", stock.getCategory());
      stockList.add(stockMap);
    }
    return ResponseEntity.ok(stockList);
  }

  /**
   * Updates the details of a specific stock item.
   *
   * @param id The ID of the stock item to update.
   * @param updatedStock A Stock object with updated values.
   * @return A ResponseEntity containing the updated stock item, or 404 error.
   */
  @PatchMapping("/{id}")
  public ResponseEntity<Stock> updateStock(@PathVariable("id") Integer id, @RequestBody Stock updatedStock) {
      Optional<Stock> optionalStock = stockRepository.findById(id);
      if (!optionalStock.isPresent()) {
          return ResponseEntity.notFound().build();
      }
      
      Stock stock = optionalStock.get();
      if (updatedStock.getTitle() != null) stock.setTitle(updatedStock.getTitle());
      if (updatedStock.getQuantity() != null) stock.setQuantity(updatedStock.getQuantity());
      if (updatedStock.getCategory() != null) stock.setCategory(updatedStock.getCategory());
      
      Stock savedStock = stockRepository.save(stock);
      return ResponseEntity.ok(savedStock);
  }
}
