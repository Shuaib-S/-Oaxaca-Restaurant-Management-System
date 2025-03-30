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

@RestController
@RequestMapping("/api/stock")
public class StockController {

  private final StockRepository stockRepository;

  @Autowired
  public StockController(StockRepository stockRepository) {
    this.stockRepository = stockRepository;
  }

  /**
   * Gets a list of all the stocks.
   * 
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
   * Updates the Stock's details.
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
