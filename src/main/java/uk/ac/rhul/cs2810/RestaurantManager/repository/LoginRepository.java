package uk.ac.rhul.cs2810.RestaurantManager.repository;

import org.springframework.data.repository.CrudRepository;
import uk.ac.rhul.cs2810.RestaurantManager.model.Login;

/**
 * Repository for performing operations on logins.
 */
public interface LoginRepository extends CrudRepository<Login, Integer> {

}
