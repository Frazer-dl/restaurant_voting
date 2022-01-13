package ru.restaurant_voting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.restaurant_voting.model.Restaurant;
import ru.restaurant_voting.service.RestaurantService;
import ru.restaurant_voting.web.AuthUser;

import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "restaurant")
public class UserRestaurantController {

    static final String REST_URL = "/api/profile/restaurant";

    private final RestaurantService restaurantService;

    public UserRestaurantController(RestaurantService service) {
        this.restaurantService = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        return ResponseEntity.of(restaurantService.get(authUser.id(), id));
    }

    @GetMapping("/{id}/withMenus")
    public Restaurant getWithMenus(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        return restaurantService.getWithMenus(authUser.id(), id);
    }

    @GetMapping
    @Cacheable
    public List<Restaurant> getAll(@AuthenticationPrincipal AuthUser authUser) {
        return restaurantService.getAll(authUser.id());
    }
}
