package ru.restaurant_voting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.restaurant_voting.model.Menu;
import ru.restaurant_voting.service.MenuService;
import ru.restaurant_voting.util.validation.ValidationUtil;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
// TODO: cache only most requested data!
@CacheConfig(cacheNames = "menu")
public class AdminMenuController {

    static final String REST_URL = "/api/admin/restaurant/{id}/menu";

    private final MenuService menuService;

    public AdminMenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping()
    @Cacheable
    public List<Menu> getAll(@PathVariable int id) {
        return menuService.getAll(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    public ResponseEntity<List<Menu>> create(@Valid @RequestBody List<Menu> menus, @PathVariable int id) {
        log.info("create {}", menus);
        menus.forEach(ValidationUtil::checkNew);
        List<Menu> created = menus.stream().map(m -> menuService.save(m, id)).collect(Collectors.toList());
        return ResponseEntity.ok(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void update(@Valid @RequestBody List<Menu> menus, @PathVariable int id) {
        log.info("update {} for restaurant {}", menus, id);
        menus.forEach(m -> ValidationUtil.assureIdConsistent(m, m.getId()));
        menuService.update(menus);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Valid @RequestBody List<Menu> menus, @PathVariable int id) {
        menuService.delete(menus, id);
    }
}
