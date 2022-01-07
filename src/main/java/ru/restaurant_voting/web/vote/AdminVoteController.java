package ru.restaurant_voting.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.restaurant_voting.model.Vote;
import ru.restaurant_voting.repository.VoteRepository;
import ru.restaurant_voting.service.VoteService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "vote")
public class AdminVoteController {

    static final String REST_URL = "/api/admin/vote";

    private final VoteRepository voteRepository;
    private final VoteService voteService;

    public AdminVoteController(VoteRepository voteRepository, VoteService voteService) {
        this.voteRepository = voteRepository;
        this.voteService = voteService;
    }

    @GetMapping()
    @Cacheable
    public List<Vote> getAll() {
        return voteRepository.getAll();
    }

    @GetMapping("/rest={id}")
    public List<Vote> getByRestaurantId(@PathVariable int id) {
        return voteRepository.getByRestaurantId(id).stream().toList();
    }

    @GetMapping("/user={id}")
    public Vote getByUserId(@PathVariable int id) {
        return voteRepository.getByUserId(id).get();
    }

    @GetMapping("/date")
    @Cacheable
    public List<Vote> getByDate(@RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        return voteService.getBetweenHalfOpen(date);
    }
}
