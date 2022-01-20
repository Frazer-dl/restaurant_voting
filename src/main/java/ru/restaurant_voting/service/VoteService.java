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

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Transactional
    public Vote save(int id, int restaurantId) {
        Optional<Vote> vote = voteRepository.getByUserIdForToDay(id);
        if (vote.isPresent()) {
            if (DateTimeUtil.isUserVoteInTime()) {
                vote.get().setRestaurant(restaurantRepository.getById(restaurantId));
                return vote.get();
            } else {
                throw new IllegalRequestDataException("Can't update vote it's too late");
            }
        }
        log.info("save for restaurant {} from user {}", restaurantId, id);
        return voteRepository.save(new Vote(null, userRepository.getById(id), restaurantRepository.getById(restaurantId)));
    }
}
