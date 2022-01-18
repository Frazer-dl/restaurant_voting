package ru.restaurant_voting.web.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import ru.restaurant_voting.model.MenuItem;
import ru.restaurant_voting.repository.MenuRepository;

import java.time.LocalDate;
import java.util.List;

@Slf4j
public abstract class AbstractMenuController {

    @Autowired
    protected MenuRepository menuRepository;

    public List<MenuItem> getAll(@PathVariable int id) {
        return menuRepository.getAll(LocalDate.now(), id);
    }
}
