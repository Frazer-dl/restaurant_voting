package ru.restaurant_voting;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import ru.restaurant_voting.model.Vote;
import ru.restaurant_voting.repository.RestaurantRepository;
import ru.restaurant_voting.repository.UserRepository;
import ru.restaurant_voting.repository.VoteRepository;

@EnableCaching
@SpringBootApplication
@AllArgsConstructor
public class RestaurantVotingRestApplication implements ApplicationRunner {
    private final VoteRepository voteRepository;

    public static void main(String[] args) {
        SpringApplication.run(RestaurantVotingRestApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        voteRepository.save(new Vote(null, 1, 1));
        voteRepository.save(new Vote(null, 2, 1));
        voteRepository.save(new Vote(null, 3, 2));
    }
}
