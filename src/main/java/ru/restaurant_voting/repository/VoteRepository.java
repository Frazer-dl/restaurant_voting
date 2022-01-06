package ru.restaurant_voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant_voting.model.Restaurant;
import ru.restaurant_voting.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
}
