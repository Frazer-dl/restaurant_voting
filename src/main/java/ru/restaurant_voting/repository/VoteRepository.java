package ru.restaurant_voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant_voting.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v ORDER BY v.date")
    List<Vote> getAll();

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId ORDER BY v.date")
    List<Vote> getByUserId(int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id = :userId AND v.date = current_date order by v.date")
    Optional<Vote> getByUserIdForToDay(int userId);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id = :restaurantId ORDER BY v.date")
    List<Vote> getByRestaurantId(int restaurantId);

    @Query("SELECT v FROM  Vote v WHERE v.date = :date ORDER BY v.date")
    List<Vote> getByDate(LocalDate date);
}