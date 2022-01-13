package ru.restaurant_voting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant_voting.error.IllegalRequestDataException;
import ru.restaurant_voting.model.Restaurant;
import ru.restaurant_voting.model.Vote;
import ru.restaurant_voting.repository.VoteRepository;
import ru.restaurant_voting.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    @Transactional
    public Vote setVote(int id, int restaurantId) {
        if (voteRepository.getByUserId(id).isPresent()) {
            Vote vote = voteRepository.getByUserId(id).get();
            if (DateTimeUtil.isUserVoteInTime(LocalDateTime.now(), vote.getDateTime())) {
                System.out.println(vote.getDateTime());
                return updateVote(vote, id, restaurantId);
            } else {
                throw new IllegalRequestDataException("Can't update vote it's too late");
            }
        }
        log.info("setVote for restaurant {} from user {}", restaurantId, id);
        return voteRepository.save(new Vote(null, id, restaurantId));
    }

    public Vote updateVote(Vote vote, int id, int restaurantId) {
        log.info("updateVote for restaurant {} from user {}", restaurantId, id);
        vote.setRestaurantId(restaurantId);
        return vote;
    }

    public List<Vote> getBetweenHalfOpen(LocalDateTime dateTime) {
        return voteRepository.getBetweenHalfOpen(DateTimeUtil.getStartDate(dateTime),
                DateTimeUtil.getEndDate(dateTime));
    }

    public List<Restaurant> getMostPopularRestaurant(int quantity) {
        int q = quantity;
        List<Restaurant> restaurantNames = voteRepository.getMostPopularRestaurant();
        if (restaurantNames.size() < q) q = restaurantNames.size();
        return restaurantNames.subList(0, q);
    }
}
