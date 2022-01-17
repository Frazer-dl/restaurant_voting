package ru.restaurant_voting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant_voting.error.IllegalRequestDataException;
import ru.restaurant_voting.model.Vote;
import ru.restaurant_voting.repository.RestaurantRepository;
import ru.restaurant_voting.repository.UserRepository;
import ru.restaurant_voting.repository.VoteRepository;
import ru.restaurant_voting.util.DateTimeUtil;

@Service
@Slf4j
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Transactional
    public Vote save(int id, int restaurantId) {
        if (voteRepository.getByUserIdForToDay(id).isPresent()) {
            Vote vote = voteRepository.getByUserIdForToDay(id).get();
            if (DateTimeUtil.isUserVoteInTime(vote.getDate())) {
                return updateVote(vote, id, restaurantId);
            } else {
                throw new IllegalRequestDataException("Can't update vote it's too late");
            }
        }
        log.info("save for restaurant {} from user {}", restaurantId, id);
        return voteRepository.save(new Vote(null, userRepository.getById(id), restaurantRepository.getById(restaurantId)));
    }

    private Vote updateVote(Vote vote, int id, int restaurantId) {
        log.info("updateVote for restaurant {} from user {}", restaurantId, id);
        vote.setRestaurant(restaurantRepository.getById(restaurantId));
        return vote;
    }
}
