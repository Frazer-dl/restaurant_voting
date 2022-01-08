package ru.restaurant_voting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.restaurant_voting.model.Menu;
import ru.restaurant_voting.repository.MenuRepository;
import ru.restaurant_voting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public List<Menu> getAllForToDay(int id) {
        log.info("getAll menus for restaurant {} this day", id);
        return menuRepository.getAll(LocalDate.now().atTime(LocalTime.MIDNIGHT), LocalDate.now().atTime(LocalTime.MAX), id);
    }

    public List<Menu> getAll(int id) {
        log.info("getAll menus for restaurant {}", id);
        return menuRepository.getAll(id);
    }

    public Menu save(Menu menu, int id) {
        log.info("save menu {} for restaurant{} by admin", menu, id);
        return menuRepository.save(prepareToSave(menu, id));
    }


    public void update(List<Menu> menus, int id) {
        log.info("update menus {} for restaurant by admin", menus);
        menus.forEach(m -> prepareToSave(m, id));
        menuRepository.saveAll(menus);
    }

    public void delete(List<Menu> menus, int id) {
        log.info("delete menus {} for restaurant {} by admin", menus, id);
        menuRepository.deleteAll(menus);
    }

    public Menu prepareToSave(Menu menu, int id) {
        menu.setRestaurant(restaurantRepository.get(id).get());
        return menu;
    }
}
