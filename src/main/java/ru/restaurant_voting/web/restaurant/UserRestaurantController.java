package ru.restaurant_voting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.restaurant_voting.error.IllegalRequestDataException;
import ru.restaurant_voting.model.Restaurant;
import ru.restaurant_voting.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "restaurants")
public class UserRestaurantController {

    static final String REST_URL = "/api/restaurants";

    private final RestaurantRepository restaurantRepository;

    public UserRestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/{id}")
    @CacheEvict(allEntries = true)
    public Restaurant get(@PathVariable int id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isPresent()) {
            return restaurant.get();
        } else {
            throw new IllegalRequestDataException("Restaurant with id=" + id + " not exist.");
        }
    }

    @GetMapping("/{id}/with-menu")
    @CacheEvict(allEntries = true)
    public Restaurant getWithMenu(@PathVariable int id) {
        return restaurantRepository.getWithMenu(id);
    }

    @GetMapping
    @CacheEvict(allEntries = true)
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @GetMapping("/with-menu")
    @CacheEvict(allEntries = true)
    public List<Restaurant> getAllWithMenusForToDay() {
        return restaurantRepository.getAllWithMenuForToDay();
    }

    @GetMapping("/top={q}")
    @CacheEvict(allEntries = true)
    public List<Restaurant> getMostPopularRestaurant(@PathVariable int q) {
        return restaurantRepository.getMostPopularRestaurant(q);
    }
}
