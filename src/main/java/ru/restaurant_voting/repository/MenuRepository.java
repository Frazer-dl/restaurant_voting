package ru.restaurant_voting.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant_voting.model.MenuItem;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<MenuItem> {

    @Query("SELECT m FROM MenuItem m WHERE m.restaurant.id = :restaurantId AND m.date = :date ORDER BY m.date")
    List<MenuItem> getAll(LocalDate date, int restaurantId);

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM MenuItem m JOIN FETCH m.restaurant as r WHERE m.id = :id AND r.id = :restaurantId ORDER BY m.id")
    MenuItem getWithRestaurant(int id, int restaurantId);

    @Transactional
    @Modifying
    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("DELETE FROM MenuItem m WHERE m.id=:id AND m.restaurant.id = :restaurantId")
    int delete(int id, int restaurantId);
}
