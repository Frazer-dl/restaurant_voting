package ru.restaurant_voting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.restaurant_voting.model.Vote;
import ru.restaurant_voting.repository.VoteRepository;
import ru.restaurant_voting.web.AbstractControllerTest;
import ru.restaurant_voting.web.restaurant.RestaurantTestData;
import ru.restaurant_voting.web.user.UserTestData;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserVoteControllerTest extends AbstractControllerTest {

    static final String REST_URL = "/api/profile/vote";

    @Autowired
    VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void getMostPopularRestaurantName() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/top=2"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.RESTAURANT_MATCHER.contentJson(RestaurantTestData.restaurant_1, RestaurantTestData.restaurant_2));
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_2_MAIL)
    void create() throws Exception {
        voteRepository.delete(VoteTestData.VOTE_3_ID);
        ResultActions actions = perform(MockMvcRequestBuilders.put(REST_URL + "/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        Vote vote = VoteTestData.VOTE_MATCHER.readFromJson(actions);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(vote.getRestaurant(), VoteTestData.USER_2_VOTE_FOR_RESTAURANT_1.getRestaurant());
        UserTestData.USER_MATCHER.assertMatch(vote.getUser(), VoteTestData.USER_2_VOTE_FOR_RESTAURANT_1.getUser());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void update() throws Exception {
        ResultActions actions = perform(MockMvcRequestBuilders.put(REST_URL + "/2"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        Vote vote = VoteTestData.VOTE_MATCHER.readFromJson(actions);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(vote.getRestaurant(), VoteTestData.USER_1_VOTE_FOR_RESTAURANT_2.getRestaurant());
        UserTestData.USER_MATCHER.assertMatch(vote.getUser(), VoteTestData.USER_1_VOTE_FOR_RESTAURANT_2.getUser());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_3_MAIL)
    void updateNotInTime() throws Exception {
        voteRepository.save(VoteTestData.getNew());
        perform(MockMvcRequestBuilders.put(REST_URL + "/1"))
                .andExpect(status().is4xxClientError())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTE_MATCHER.contentJson(VoteTestData.ILLEGAL_VOTE));
    }
}