package ru.restaurant_voting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.restaurant_voting.model.Restaurant;
import ru.restaurant_voting.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public Optional<Restaurant> get(int id, int restaurantId) {
        log.info("get restaurant {} for user {}", restaurantId, id);
        return restaurantRepository.get(restaurantId);
    }

    public Optional<Restaurant> get(int restaurantId) {
        log.info("get restaurant {} for admin", restaurantId);
        return restaurantRepository.get(restaurantId);
    }

    public List<Restaurant> getAll(int id) {
        log.info("getAll restaurants for user {}", id);
        return restaurantRepository.getAll();
    }

    public List<Restaurant> getAll() {
        log.info("getAll restaurants for admin");
        return restaurantRepository.getAll();
    }

    public void delete(int id) {
        log.info("delete restaurant {} by admin", id);
        restaurantRepository.deleteExisted(id);
    }

    public Restaurant save(Restaurant restaurant) {
        log.info("save restaurant {} by admin", restaurant);
        return restaurantRepository.save(restaurant);
    }

    public Restaurant getWithMenus(int id, int restaurantId) {
        log.info("getWithMenus restaurant {} by user {}", restaurantId, id);
        return restaurantRepository.getWithMenus(id);
    }
}
