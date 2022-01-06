package ru.restaurant_voting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.restaurant_voting.model.Menu;
import ru.restaurant_voting.repository.MenuRepository;
import ru.restaurant_voting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

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
        menu.setRestaurant(restaurantRepository.get(id).get());
        return menuRepository.save(menu);
    }

    @Transactional
    public void update(List<Menu> menus) {
        log.info("update menus {} for restaurant by admin", menus);
        List<Menu> menus1 = menus.stream()
                .peek(menu -> {
                    Menu menuFromDb = menuRepository.getById(menu.getId());
                    menuFromDb.setName(menu.getName());
                    menuFromDb.setPrice(menu.getPrice());
                    menuFromDb.setDateTime(menu.getDateTime());

                }).collect(Collectors.toList());
        menus1.forEach(System.out::println);
    }
}
