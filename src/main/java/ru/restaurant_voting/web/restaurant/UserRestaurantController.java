package ru.restaurant_voting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.restaurant_voting.model.Restaurant;
import ru.restaurant_voting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        return ResponseEntity.ok(restaurantRepository.getById(id));
    }

    @GetMapping("/{id}/with-menu")
    public Restaurant getWithMenus(@PathVariable int id) {
        Restaurant restaurant = restaurantRepository.getWithMenus(id);
        restaurant.setMenu(restaurant.getMenu().stream()
                .filter(m -> Objects.equals(m.getDate(), LocalDate.now()))
                .collect(Collectors.toList()));
        return restaurant;
    }

    @GetMapping
    @Cacheable
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @GetMapping("/top={q}")
    public List<Restaurant> getMostPopularRestaurantName(@PathVariable int q) {
        return restaurantRepository.getMostPopularRestaurant(q);
    }
}
