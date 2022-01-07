package ru.restaurant_voting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant_voting.model.Vote;
import ru.restaurant_voting.repository.VoteRepository;
import ru.restaurant_voting.util.DateTimeUtil;

@Service
@Slf4j
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    @Transactional
    public Vote setVote(int id, int restaurantId) {
        if (voteRepository.getByUserId(id).isPresent()) {
            Vote vote = voteRepository.getByUserId(id).get();
            if (DateTimeUtil.isUserVoteInTime(vote.getDate())) {
                return updateVote(vote, id, restaurantId);
            } else {
                log.info("setVote for restaurant {} from user {} can't update vote it's too late", restaurantId, id);
                return vote;
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
}
