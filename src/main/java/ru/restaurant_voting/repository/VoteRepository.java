package ru.restaurant_voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant_voting.model.Vote;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v ORDER BY v.date")
    List<Vote> getAll();

    @Query("SELECT v FROM Vote v WHERE v.userId = :userId")
    Optional<Vote> getByUserId(int userId);

    @Query("SELECT v FROM Vote v WHERE v.restaurantId = :restaurantId")
    List<Vote> getByRestaurantId(int restaurantId);

    @Query("SELECT v FROM  Vote v WHERE v.date >= :startDate AND v.date < :endDate ORDER BY v.date")
    List<Vote> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT r.name as counted FROM Vote AS v INNER JOIN Restaurant r on r.id = v.restaurantId GROUP BY r.name ORDER BY counted desc")
    List<String> getMostPopularRestaurant();
}
