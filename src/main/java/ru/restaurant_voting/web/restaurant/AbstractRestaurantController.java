package ru.restaurant_voting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import ru.restaurant_voting.error.IllegalRequestDataException;
import ru.restaurant_voting.model.Restaurant;
import ru.restaurant_voting.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@CacheConfig(cacheNames = "restaurants")
public abstract class AbstractRestaurantController {

    @Autowired
    protected RestaurantRepository restaurantRepository;

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @CacheEvict(allEntries = true)
    public Restaurant get(@PathVariable int id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isPresent()) {
            return restaurant.get();
        } else {
            throw new IllegalRequestDataException("Restaurant with id=" + id + " not exist.");
        }
    }
}
