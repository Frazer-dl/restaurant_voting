package ru.restaurant_voting.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.restaurant_voting.error.IllegalRequestDataException;
import ru.restaurant_voting.model.Vote;
import ru.restaurant_voting.repository.VoteRepository;
import ru.restaurant_voting.service.VoteService;
import ru.restaurant_voting.util.validation.ValidationUtil;
import ru.restaurant_voting.web.AuthUser;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "votes")
public class UserVoteController {

    static final String REST_URL = "/api/profile/votes";

    private final VoteService voteService;
    private final VoteRepository voteRepository;

    public UserVoteController(VoteService voteService, VoteRepository voteRepository) {
        this.voteService = voteService;
        this.voteRepository = voteRepository;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    public ResponseEntity<Vote> create(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody Vote vote) {
        log.info("create {}", vote);
        int userId = authUser.id();
        ValidationUtil.checkNew(vote);
        Vote created = voteService.save(userId, vote.getRestaurant().id());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping
    @Cacheable
    public Vote get(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        Optional<Vote> vote = voteRepository.getByUserIdForToDay(userId);
        if (vote.isPresent()) {
            return vote.get();
        } else {
            throw new IllegalRequestDataException("This user have not voted yet for today.");
        }
    }
}
