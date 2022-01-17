package ru.restaurant_voting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.restaurant_voting.model.Restaurant;

import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "restaurants")
public class UserRestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/api/restaurants";

    @GetMapping
    @Cacheable
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @GetMapping("/{id}")
    @Cacheable
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping("/{id}/with-menu")
    @CacheEvict(allEntries = true)
    public Restaurant getWithMenu(@PathVariable int id) {
        return restaurantRepository.getWithMenu(id);
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
