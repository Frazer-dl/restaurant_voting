package ru.restaurant_voting.web.menu;

import ru.restaurant_voting.model.MenuItem;
import ru.restaurant_voting.web.MatcherFactory;
import ru.restaurant_voting.web.restaurant.RestaurantTestData;

import java.time.LocalDate;
import java.util.List;

public class MenuTestData {
    public static final MatcherFactory.Matcher<MenuItem> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(MenuItem.class, "restaurant", "dateTime");

    public static final int MENU_1_ID = 1;
    public static final int MENU_2_ID = 2;
    public static final int MENU_3_ID = 3;
    public static final int MENU_4_ID = 4;
    public static final int MENU_5_ID = 4;
    public static final String MENU_1_NAME = "Курица с ананасами";
    public static final String MENU_2_NAME = "Салат Оливье";
    public static final String MENU_3_NAME = "Бургер с говядиной";
    public static final String MENU_4_NAME = "Соус Невероятный";
    public static final String MENU_5_NAME = "Menu Item 3";
    public static final int PRICE_200 = 200;
    public static final int PRICE_250 = 250;
    public static final int PRICE_500 = 500;
    public static final int PRICE_50 = 50;

    public static final MenuItem menu_1 = new MenuItem(MENU_1_ID, MENU_1_NAME, LocalDate.now(), PRICE_200, RestaurantTestData.restaurant_1);
    public static final MenuItem menu_2 = new MenuItem(MENU_2_ID, MENU_2_NAME, LocalDate.now(), PRICE_250, RestaurantTestData.restaurant_1);
    public static final List<MenuItem> menus_1 = List.of(menu_1, menu_2);
    public static final MenuItem menu_3 = new MenuItem(MENU_3_ID, MENU_3_NAME, LocalDate.now(), PRICE_500, RestaurantTestData.restaurant_2);
    public static final MenuItem menu_4 = new MenuItem(MENU_4_ID, MENU_4_NAME, LocalDate.now(), PRICE_50, RestaurantTestData.restaurant_2);
    public static final List<MenuItem> menus_2 = List.of(menu_3, menu_4);
    public static final MenuItem menu_5 = new MenuItem(MENU_5_ID, MENU_5_NAME, LocalDate.now(), PRICE_50, RestaurantTestData.restaurant_3);
    public static final List<MenuItem> menus_3 = List.of(menu_5);
    public static final List<MenuItem> menus = List.of(menu_1, menu_2, menu_3, menu_4, menu_5);

    public static MenuItem getNew() {
        return new MenuItem(null, "newMenu", LocalDate.now(), PRICE_200, RestaurantTestData.restaurant_1);
    }

    public static MenuItem getUpdated() {
        return new MenuItem(MENU_1_ID, "updated", LocalDate.now(), PRICE_50, RestaurantTestData.restaurant_1);
    }
}
