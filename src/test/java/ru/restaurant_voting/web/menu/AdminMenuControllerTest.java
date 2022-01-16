package ru.restaurant_voting.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.restaurant_voting.model.MenuItems;
import ru.restaurant_voting.repository.MenuRepository;
import ru.restaurant_voting.util.JsonUtil;
import ru.restaurant_voting.web.AbstractControllerTest;
import ru.restaurant_voting.web.user.UserTestData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminMenuControllerTest extends AbstractControllerTest {

    static final String REST_URL_FOR_RESTAURANT_1 = "/api/admin/restaurant/1/menus";

    @Autowired
    MenuRepository menuRepository;

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_FOR_RESTAURANT_1))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.MENU_MATCHER.contentJson(MenuTestData.menu_1, MenuTestData.menu_2));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void create() throws Exception {
        MenuItems newMenu = MenuTestData.getNew();
        List<MenuItems> newMenus = List.of(newMenu);
        perform(MockMvcRequestBuilders.post(REST_URL_FOR_RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenus)))
                .andExpect(status().isOk());
        int newMenuId = MenuTestData.menus.size() + 1;
        newMenu.setId(newMenuId);
        MenuTestData.MENU_MATCHER.assertMatch(menuRepository.getById(newMenuId), newMenu);
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void update() throws Exception {
        MenuItems updated = MenuTestData.getUpdated();
        List<MenuItems> updatedMenus = List.of(updated);
        perform(MockMvcRequestBuilders.put(REST_URL_FOR_RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedMenus)))
                .andDo(print())
                .andExpect(status().isNoContent());
        MenuTestData.MENU_MATCHER.assertMatch(menuRepository.findById(MenuTestData.MENU_1_ID).orElse(null), updated);
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_FOR_RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MenuTestData.menus_1)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(menuRepository.findById(MenuTestData.MENU_1_ID).isPresent());
        assertFalse(menuRepository.findById(MenuTestData.MENU_2_ID).isPresent());
    }
}