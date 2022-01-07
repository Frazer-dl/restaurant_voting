package ru.restaurant_voting.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.restaurant_voting.error.IllegalRequestDataException;
import ru.restaurant_voting.model.Vote;
import ru.restaurant_voting.service.RestaurantService;
import ru.restaurant_voting.service.VoteService;
import ru.restaurant_voting.web.AuthUser;

import java.util.List;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "vote")
public class UserVoteController {

    static final String REST_URL = "/api/profile/vote";

    private final RestaurantService restaurantService;
    private final VoteService voteService;

    public UserVoteController(RestaurantService restaurantService, VoteService voteService) {
        this.restaurantService = restaurantService;
        this.voteService = voteService;
    }

    @GetMapping("/vote/{q}")
    @Cacheable
    public List<String> getMostPopularRestaurantName(@PathVariable int q) {
        return voteService.getMostPopularRestaurant(q);
    }

    @PutMapping(value = "/{id}")
    @CacheEvict(allEntries = true)
    public ResponseEntity<Vote> createOrUpdateVote(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        int userId = authUser.id();
        log.info("voting restaurant {} for user {}", id, userId);
        if (restaurantService.get(userId, id).isPresent()) {
            return ResponseEntity.ok(voteService.setVote(userId, id));
        } else {
            throw new IllegalRequestDataException("Restaurant with id {" + id + "} not exist");
        }
    }
}
