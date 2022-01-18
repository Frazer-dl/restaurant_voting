package ru.restaurant_voting.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.restaurant_voting.error.IllegalRequestDataException;
import ru.restaurant_voting.model.Vote;
import ru.restaurant_voting.repository.VoteRepository;
import ru.restaurant_voting.service.VoteService;
import ru.restaurant_voting.web.AuthUser;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class UserVoteController {

    static final String REST_URL = "/api/profile/votes";

    private final VoteService voteService;
    private final VoteRepository voteRepository;

    public UserVoteController(VoteService voteService, VoteRepository voteRepository) {
        this.voteService = voteService;
        this.voteRepository = voteRepository;
    }

    @PostMapping
    public ResponseEntity<Vote> create(@AuthenticationPrincipal AuthUser authUser, @RequestParam int restaurantId) {
        log.info("create user {} vote for {}", authUser.id(), restaurantId);
        int userId = authUser.id();
        Vote created = voteService.save(userId, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping
    public Vote get(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        Optional<Vote> vote = voteRepository.getByUserIdForToDay(userId);
        if (vote.isPresent()) {
            return vote.get();
        } else {
            throw new IllegalRequestDataException("This user have not voted yet for today.");
        }
    }

    @GetMapping("/history")
    public List<Vote> getHistory(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        Optional<List<Vote>> votes = voteRepository.getHistory(userId);
        if (votes.isPresent()) {
            return votes.get();
        } else {
            throw new IllegalRequestDataException("This user have not voted yet.");
        }
    }
}
