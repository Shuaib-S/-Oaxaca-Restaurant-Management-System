package uk.ac.rhul.cs2810.RestaurantManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uk.ac.rhul.cs2810.RestaurantManager.model.TableAssistance;

@Repository
public interface TableAssistanceRepository extends JpaRepository<TableAssistance, Integer> {

}
