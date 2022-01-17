package ru.restaurant_voting.web.vote;

import ru.restaurant_voting.model.Vote;
import ru.restaurant_voting.web.MatcherFactory;
import ru.restaurant_voting.web.restaurant.RestaurantTestData;
import ru.restaurant_voting.web.user.UserTestData;

import java.time.LocalDate;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "dateTime");

    public static final int VOTE_1_ID = 1;
    public static final int VOTE_3_ID = 3;
    public static final int VOTE_4_ID = 4;
    public static final Vote USER_2_VOTE_FOR_RESTAURANT_1 = new Vote(VOTE_4_ID, UserTestData.user2, RestaurantTestData.restaurant_1);
    public static final Vote USER_1_VOTE_FOR_RESTAURANT_2 = new Vote(VOTE_1_ID, UserTestData.user, RestaurantTestData.restaurant_2);
    public static final Vote ILLEGAL_VOTE = new Vote(null, null, null);

    public static Vote getNew() {
        return new Vote(null, UserTestData.user3, RestaurantTestData.restaurant_2, LocalDate.now().minusDays(2L));
    }
}
