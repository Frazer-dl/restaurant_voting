package ru.restaurant_voting.web.menu;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.restaurant_voting.web.AbstractControllerTest;
import ru.restaurant_voting.web.user.UserTestData;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserMenuControllerTest extends AbstractControllerTest {

    private static final String REST_URL_FOR_RESTAURANT_1 = "/api/profile/restaurant/1/menu";

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getAllMenuForToDay() throws Exception {
        MenuTestData.MENU_MATCHER.contentJson(MenuTestData.menu_1, MenuTestData.menu_2);
        perform(MockMvcRequestBuilders.get(REST_URL_FOR_RESTAURANT_1 + "/today"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MenuTestData.MENU_MATCHER.contentJson(MenuTestData.menu_1, MenuTestData.menu_2));
    }
}