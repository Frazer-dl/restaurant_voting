package ru.restaurant_voting.web.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.restaurant_voting.error.IllegalRequestDataException;
import ru.restaurant_voting.model.MenuItem;
import ru.restaurant_voting.repository.MenuRepository;
import ru.restaurant_voting.repository.RestaurantRepository;
import ru.restaurant_voting.util.validation.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "menus")
public class AdminMenuController extends AbstractMenuController {

    static final String REST_URL = "/api/admin/restaurants/{id}/menu-item";

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public AdminMenuController(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/by-date")
    public List<MenuItem> getAllByDate(@PathVariable int id, @RequestParam("date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return menuRepository.getAll(date, id);
    }

    @GetMapping
    @Cacheable
    public List<MenuItem> getAll(@PathVariable int id) {
        return super.getAll(id);
    }

    @GetMapping("/{menuId}")
    public MenuItem get(@PathVariable int menuId, @PathVariable int id) {
        return menuRepository.getWithRestaurant(menuId, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @CacheEvict(allEntries = true)
    public ResponseEntity<MenuItem> create(@Valid @RequestBody MenuItem menuItem, @PathVariable int id) {
        log.info("create {}", menuItem);
        ValidationUtil.checkNew(menuItem);
        menuItem.setRestaurant(restaurantRepository.findById(id).get());
        MenuItem created = menuRepository.save(menuItem);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/" + created.getId())
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(allEntries = true)
    public void update(@Valid @RequestBody MenuItem menuItem, @PathVariable int menuId, @PathVariable int id) {
        log.info("update {} for restaurant {}", menuItem, menuId);
        MenuItem previous = menuRepository.getWithRestaurant(menuId, id);
        if (previous == null)
            throw new IllegalRequestDataException("Can't find restaurant=" + id + " menu item=" + menuId);
        ValidationUtil.assureIdConsistent(menuItem.getRestaurant(), id);
        ValidationUtil.assureIdConsistent(menuItem, menuId);
        previous.setName(menuItem.getName());
        previous.setPrice(menuItem.getPrice());
        previous.setDate(menuItem.getDate());
    }

    @DeleteMapping("/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void delete(@PathVariable int menuId, @PathVariable int id) {
        menuRepository.delete(menuId, id);
    }
}
