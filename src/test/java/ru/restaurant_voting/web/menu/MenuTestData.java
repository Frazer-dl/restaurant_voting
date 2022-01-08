package ru.restaurant_voting.web.menu;

import ru.restaurant_voting.model.Menu;
import ru.restaurant_voting.web.MatcherFactory;
import ru.restaurant_voting.web.restaurant.RestaurantTestData;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MenuTestData {
    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Menu.class, "restaurant", "dateTime");

    public static final int MENU_1_ID = 1;
    public static final int MENU_2_ID = 2;
    public static final int MENU_3_ID = 3;
    public static final int MENU_4_ID = 4;
    public static final String MENU_1_NAME = "Курица с ананасами";
    public static final String MENU_2_NAME = "Салат Оливье";
    public static final String MENU_3_NAME = "Бургер с говядиной";
    public static final String MENU_4_NAME = "Соус Невероятный";
    public static final int PRICE_200 = 200;
    public static final int PRICE_250 = 250;
    public static final int PRICE_500 = 500;
    public static final int PRICE_50 = 50;

    public static final Menu menu_1 = new Menu(MENU_1_ID, MENU_1_NAME, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), PRICE_200, RestaurantTestData.restaurant_1);
    public static final Menu menu_2 = new Menu(MENU_2_ID, MENU_2_NAME, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), PRICE_250, RestaurantTestData.restaurant_1);
    public static final Menu menu_3 = new Menu(MENU_3_ID, MENU_3_NAME, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), PRICE_500, RestaurantTestData.restaurant_2);
    public static final Menu menu_4 = new Menu(MENU_4_ID, MENU_4_NAME, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), PRICE_50, RestaurantTestData.restaurant_2);

    public static final List<Menu> menus_1 = List.of(menu_1, menu_2);
    public static final List<Menu> menus_2 = List.of(menu_3, menu_4);
    public static final List<Menu> menus = List.of(menu_1, menu_2, menu_3, menu_4);

    public static Menu getNew() {
        return new Menu(null, "newMenu", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), PRICE_200, RestaurantTestData.restaurant_1);
    }

    public static Menu getUpdated() {
        return new Menu(MENU_1_ID, "updated", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), PRICE_50, RestaurantTestData.restaurant_1);
    }
}
