package ru.restaurant_voting.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant_voting.model.Restaurant;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant>{
    @Query("SELECT r FROM Restaurant r ORDER BY r.name")
    List<Restaurant> getAll();

    @Query("SELECT r FROM Restaurant r WHERE r.id = :restaurantId ORDER BY r.name")
    Optional<Restaurant> get(int restaurantId);

    @EntityGraph(attributePaths = {"menu"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r WHERE r.id= :id")
    Restaurant getWithMenus(int id);

    @Query(value = "SELECT r.* FROM Vote v JOIN Restaurant r on v.RESTAURANT_ID = r.ID " +
            "group by v.RESTAURANT_ID ORDER BY COUNT(RESTAURANT_ID) DESC LIMIT :q", nativeQuery = true)
    List<Restaurant> getMostPopularRestaurant(int q);
}
