package ru.restaurant_voting.web.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.restaurant_voting.model.Menu;
import ru.restaurant_voting.service.MenuService;

import java.util.List;

@RestController
@RequestMapping(value = UserMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "menu")
public class UserMenuController {

    static final String REST_URL = "/api/profile/restaurant/{id}/menu";

    private final MenuService menuService;

    public UserMenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/today")
    @Cacheable
    public List<Menu> getAllMenuForToDay(@PathVariable int id) {
        return menuService.getAllForToDay(id);
    }
}
