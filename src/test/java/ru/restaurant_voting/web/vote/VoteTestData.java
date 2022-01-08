package ru.restaurant_voting.web.vote;

import ru.restaurant_voting.model.Vote;
import ru.restaurant_voting.web.MatcherFactory;
import ru.restaurant_voting.web.restaurant.RestaurantTestData;
import ru.restaurant_voting.web.user.UserTestData;

import java.time.LocalDateTime;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "date");

    public static final int VOTE_1_ID = 1;
    public static final int VOTE_2_ID = 2;
    public static final int VOTE_3_ID = 3;
    public static final int VOTE_5_ID = 5;

    public static final Vote USER_1_VOTE_FOR_RESTAURANT_1 = new Vote(VOTE_1_ID, UserTestData.USER_ID, RestaurantTestData.RESTAURANT_1_ID);
    public static final Vote USER_2_VOTE_FOR_RESTAURANT_1 = new Vote(VOTE_2_ID, UserTestData.ADMIN_ID, RestaurantTestData.RESTAURANT_1_ID);
    public static final Vote USER_3_VOTE_FOR_RESTAURANT_2 = new Vote(VOTE_3_ID, UserTestData.USER_2_ID, RestaurantTestData.RESTAURANT_2_ID);
    public static final Vote USER_3_VOTE_FOR_RESTAURANT_1 = new Vote(VOTE_5_ID, UserTestData.USER_2_ID, RestaurantTestData.RESTAURANT_1_ID);
    public static final Vote USER_1_VOTE_FOR_RESTAURANT_2 = new Vote(VOTE_1_ID, UserTestData.USER_ID, RestaurantTestData.RESTAURANT_2_ID);
    public static final Vote ILLEGAL_VOTE = new Vote(null, null, null);

    public static Vote getNew() {
        return new Vote(null, UserTestData.USER_3_ID, RestaurantTestData.RESTAURANT_2_ID, LocalDateTime.now().minusDays(2L));
    }
}
