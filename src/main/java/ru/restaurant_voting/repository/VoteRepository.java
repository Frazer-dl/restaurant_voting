package ru.restaurant_voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant_voting.model.Restaurant;
import ru.restaurant_voting.model.Vote;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v ORDER BY v.dateTime")
    List<Vote> getAll();

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId")
    Optional<Vote> getByUserId(int userId);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id = :restaurantId")
    List<Vote> getByRestaurantId(int restaurantId);

    @Query("SELECT v FROM  Vote v WHERE v.dateTime >= :startDate AND v.dateTime < :endDate ORDER BY v.dateTime")
    List<Vote> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT r FROM Vote AS v INNER JOIN Restaurant r on r.id = v.restaurant.id GROUP BY r.name ORDER BY COUNT(v.restaurant.id) desc")
    List<Restaurant> getMostPopularRestaurant();
}
