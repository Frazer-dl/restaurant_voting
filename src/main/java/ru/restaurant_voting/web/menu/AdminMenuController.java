package ru.restaurant_voting.web.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.restaurant_voting.model.MenuItems;
import ru.restaurant_voting.repository.MenuRepository;
import ru.restaurant_voting.util.validation.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "menus")
public class AdminMenuController {

    static final String REST_URL = "/api/admin/restaurants/{id}/menu-items";

    private final MenuRepository menuRepository;

    public AdminMenuController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @GetMapping
    @Cacheable
    public List<MenuItems> getAll(@PathVariable int id, @RequestParam("date")
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return menuRepository.getAll(date, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    public ResponseEntity<MenuItems> create(@Valid @RequestBody MenuItems menuItem) {
        log.info("create {}", menuItem);
        ValidationUtil.checkNew(menuItem);
        MenuItems created = menuRepository.save(menuItem);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void update(@Valid @RequestBody MenuItems menuItem, @PathVariable int menuId,  @PathVariable int id) {
        log.info("update {} for restaurant {}", menuItem, menuId);
        ValidationUtil.assureIdConsistent(menuItem, menuId);
        ValidationUtil.assureIdConsistent(menuItem.getRestaurant(), id);
        menuRepository.save(menuItem);
    }

    @DeleteMapping("/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void delete(@PathVariable int menuId, @PathVariable String id) {
        menuRepository.delete(menuId);
    }
}
