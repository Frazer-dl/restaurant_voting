package ru.restaurant_voting.web.restaurant;

import ru.restaurant_voting.model.Restaurant;
import ru.restaurant_voting.web.MatcherFactory;
import ru.restaurant_voting.web.menu.MenuTestData;

import java.util.Collections;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menu");

    public static final Integer RESTAURANT_1_ID = 1;
    public static final Integer RESTAURANT_2_ID = 2;
    public static final String RESTAURANT_1_NAME = "Pandas";
    public static final String RESTAURANT_2_NAME = "Millennium";

    public static final Restaurant restaurant_1 = new Restaurant(RESTAURANT_1_ID, RESTAURANT_1_NAME, MenuTestData.menus_1);
    public static final Restaurant restaurant_2 = new Restaurant(RESTAURANT_2_ID, RESTAURANT_2_NAME, MenuTestData.menus_2);

    public static Restaurant getNew() {
        return new Restaurant(null, "New", Collections.emptyList());
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_1_ID, "updated", Collections.emptyList());
    }
}
