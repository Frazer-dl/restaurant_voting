package ru.restaurant_voting;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.restaurant_voting.model.Vote;
import ru.restaurant_voting.repository.RestaurantRepository;
import ru.restaurant_voting.repository.UserRepository;
import ru.restaurant_voting.repository.VoteRepository;

@SpringBootApplication
@AllArgsConstructor
public class RestaurantVotingRestApplication implements ApplicationRunner {
    UserRepository userRepository;
    RestaurantRepository restaurantRepository;
    VoteRepository voteRepository;

    public static void main(String[] args) {
        SpringApplication.run(RestaurantVotingRestApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        voteRepository.save(new Vote(10, userRepository.getById(1), restaurantRepository.get(1).get()));
        voteRepository.save(new Vote(11, userRepository.getById(2), restaurantRepository.get(1).get()));
        voteRepository.save(new Vote(12, userRepository.getById(3), restaurantRepository.get(2).get()));
    }
}
