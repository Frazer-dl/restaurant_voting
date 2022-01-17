package ru.restaurant_voting.web.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.restaurant_voting.model.MenuItem;
import ru.restaurant_voting.repository.MenuRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "menus")
public class UserMenuController {

    static final String REST_URL = "/api/restaurants/{id}/menu-item";

    private final MenuRepository menuRepository;

    public UserMenuController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @GetMapping
    @Cacheable
    public List<MenuItem> getAll(@PathVariable int id) {
        return menuRepository.getAll(LocalDate.now(), id);
    }
}
