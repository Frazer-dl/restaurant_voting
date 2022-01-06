package ru.restaurant_voting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.restaurant_voting.model.Menu;
import ru.restaurant_voting.model.Restaurant;
import ru.restaurant_voting.service.MenuService;
import ru.restaurant_voting.service.RestaurantService;
import ru.restaurant_voting.util.validation.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
// TODO: cache only most requested data!
@CacheConfig(cacheNames = "restaurant")
public class AdminRestaurantController {

    static final String REST_URL = "/api/admin/restaurant";

    private final RestaurantService restaurantService;
    private final MenuService menuService;

    public AdminRestaurantController(RestaurantService restaurantService, MenuService menuService) {
        this.restaurantService = restaurantService;
        this.menuService = menuService;
    }

    @GetMapping()
    public List<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        return ResponseEntity.of(restaurantService.get(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        restaurantService.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        if (!restaurant.getMenu().isEmpty()) {
            throw new IllegalArgumentException("To create menu use /api/restaurant/{id}/menu");
        }
        log.info("create {}", restaurant);
        ValidationUtil.checkNew(restaurant);
        Restaurant created = restaurantService.save(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update {} with id={}", restaurant, id);
        ValidationUtil.assureIdConsistent(restaurant, id);
        restaurantService.save(restaurant);
    }

    @GetMapping("/{id}/menu")
    public List<Menu> getAllMenus(@PathVariable int id) {
        return menuService.getAll(id);
    }

    @PostMapping(value="/{id}/menu", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Menu>> createMenu(@Valid @RequestBody List<Menu> menus, @PathVariable int id) {
        log.info("create {}", menus);
        menus.forEach(ValidationUtil::checkNew);
        List<Menu> created = menus.stream().map(m -> menuService.save(m, id)).collect(Collectors.toList());
        return ResponseEntity.ok(created);
    }

    @PutMapping(value = "/{id}/menu", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMenu(@Valid @RequestBody Menu menu, @PathVariable int id) {
        log.info("update {} with id={}", menu, id);
        ValidationUtil.assureIdConsistent(menu, id);
        menuService.save(menu, id);
    }
}
