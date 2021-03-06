package ru.restaurant_voting.web.user;

import ru.restaurant_voting.model.Role;
import ru.restaurant_voting.model.User;
import ru.restaurant_voting.util.JsonUtil;
import ru.restaurant_voting.web.MatcherFactory;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password");
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int USER_2_ID = 3;
    public static final int USER_3_ID = 4;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "user@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";
    public static final String USER_2_MAIL = "user2@yandex.ru";
    public static final String USER_3_MAIL = "user3@yandex.ru";
    public static final User user = new User(USER_ID, "User", USER_MAIL, "password", Role.USER);
    public static final User user2 = new User(USER_2_ID, "User2", USER_2_MAIL, "password", Role.USER);
    public static final User user3 = new User(USER_3_ID, "User3", USER_3_MAIL, "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", Role.ADMIN, Role.USER);
    public static MatcherFactory.Matcher<User> USER_WITH_MEALS_MATCHER =
            MatcherFactory.usingAssertions(User.class,
                    //     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    (a, e) -> assertThat(a).usingRecursiveComparison()
                            .ignoringFields("registered", "password").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedName", USER_MAIL, "newPass", false, new Date(), List.of(Role.ADMIN));
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
