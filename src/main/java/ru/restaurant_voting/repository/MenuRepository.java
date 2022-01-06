package ru.restaurant_voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant_voting.model.Menu;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu>{

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id = :restaurantId AND m.dateTime >= :startDate AND m.dateTime < :endDate")
    List<Menu> getAll(LocalDateTime startDate, LocalDateTime endDate, int restaurantId);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id = :restaurantId")
    List<Menu> getAll(int restaurantId);
}
