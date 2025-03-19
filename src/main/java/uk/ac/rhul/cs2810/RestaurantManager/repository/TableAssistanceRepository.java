package uk.ac.rhul.cs2810.RestaurantManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uk.ac.rhul.cs2810.RestaurantManager.model.TableAssignment;
import uk.ac.rhul.cs2810.RestaurantManager.model.TableAssistance;

@Repository
public interface TableAssistanceRepository extends JpaRepository<TableAssistance, Long> {
  List<TableAssistance> findByTableNo(Integer tableNo);
}
