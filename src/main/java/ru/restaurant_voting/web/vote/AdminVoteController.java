package ru.restaurant_voting.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.restaurant_voting.model.Vote;
import ru.restaurant_voting.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "votes")
public class AdminVoteController {

    static final String REST_URL = "/api/admin/votes";

    private final VoteRepository voteRepository;

    public AdminVoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @GetMapping
    @Cacheable
    public List<Vote> getAll() {
        return voteRepository.getAll();
    }

    @GetMapping("/rest={id}")
    public List<Vote> getByRestaurantId(@PathVariable int id) {
        return voteRepository.getByRestaurantId(id);
    }

    @GetMapping("/user={id}")
    public List<Vote> getByUserId(@PathVariable int id) {
        return voteRepository.getByUserId(id);
    }

    @GetMapping("/date")
    @Cacheable
    public List<Vote> getByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return voteRepository.getByDate(date);
    }
}
