package ru.restaurant_voting.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.restaurant_voting.model.MenuItem;
import ru.restaurant_voting.repository.MenuRepository;
import ru.restaurant_voting.util.JsonUtil;
import ru.restaurant_voting.web.AbstractControllerTest;
import ru.restaurant_voting.web.user.UserTestData;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminMenuControllerTest extends AbstractControllerTest {

    static final String REST_URL_FOR_RESTAURANT_1 = "/api/admin/restaurants/1/menu-item";

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
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_FOR_RESTAURANT_1 + "/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.MENU_MATCHER.contentJson(MenuTestData.menu_1));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void create() throws Exception {
        MenuItem newMenuItem = MenuTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_FOR_RESTAURANT_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenuItem)))
                .andExpect(status().is2xxSuccessful());
        MenuItem created = MenuTestData.MENU_MATCHER.readFromJson(action);
        newMenuItem.setId(created.getId());
        MenuTestData.MENU_MATCHER.assertMatch(created, newMenuItem);
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void update() throws Exception {
        MenuItem updated = MenuTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_FOR_RESTAURANT_1 + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        MenuTestData.MENU_MATCHER.assertMatch(menuRepository.findById(MenuTestData.MENU_1_ID).orElse(null), updated);
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_FOR_RESTAURANT_1 + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MenuTestData.menus_1)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(menuRepository.findById(MenuTestData.MENU_1_ID).isPresent());
    }
}