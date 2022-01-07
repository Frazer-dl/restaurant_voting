package ru.restaurant_voting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.restaurant_voting.error.IllegalRequestDataException;
import ru.restaurant_voting.model.Menu;
import ru.restaurant_voting.model.Restaurant;
import ru.restaurant_voting.service.MenuService;
import ru.restaurant_voting.service.RestaurantService;
import ru.restaurant_voting.service.VoteService;
import ru.restaurant_voting.web.AuthUser;

import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
// TODO: cache only most requested data!
@CacheConfig(cacheNames = "restaurant")
public class UserRestaurantController {

    static final String REST_URL = "/api/profile/restaurant";

    private final RestaurantService restaurantService;
    private final VoteService voteService;
    private final MenuService menuService;

    public UserRestaurantController(RestaurantService service, VoteService voteService, MenuService menuService) {
        this.restaurantService = service;
        this.voteService = voteService;
        this.menuService = menuService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        return ResponseEntity.of(restaurantService.get(authUser.id(), id));
    }

    @GetMapping
    @Cacheable
    public List<Restaurant> getAll(@AuthenticationPrincipal AuthUser authUser) {
        return restaurantService.getAll(authUser.id());
    }

    @PutMapping(value = "/{id}/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void createOrUpdateVote(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        int userId = authUser.id();
        log.info("voting restaurant {} for user {}", id, userId);
        if (restaurantService.get(userId, id).isPresent()) {
            voteService.setVote(userId, id);
        } else {
            throw new IllegalRequestDataException("Restaurant with id {" + id + "} not exist");
        }
    }

    @GetMapping("/{id}/menu/today")
    @Cacheable
    public List<Menu> getAllMenuForToDay(@PathVariable int id) {
        return menuService.getAllForToDay(id);
    }
}
