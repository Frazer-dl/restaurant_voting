package ru.restaurant_voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant_voting.model.MenuItems;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<MenuItems> {

    @Query("SELECT m FROM MenuItems m WHERE m.restaurant.id = :restaurantId AND m.date = :date ORDER BY m.date")
    List<MenuItems> getAll(LocalDate date, int restaurantId);

    @Query("SELECT m FROM MenuItems m WHERE m.restaurant.id = :restaurantId ORDER BY m.date")
    List<MenuItems> getAll(int restaurantId);
}
