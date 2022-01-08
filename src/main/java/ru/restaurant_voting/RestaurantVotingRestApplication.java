package ru.restaurant_voting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class RestaurantVotingRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantVotingRestApplication.class, args);
    }
}
