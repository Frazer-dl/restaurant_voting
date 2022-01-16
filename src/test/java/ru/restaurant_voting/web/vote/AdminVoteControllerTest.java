package ru.restaurant_voting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.restaurant_voting.model.Vote;
import ru.restaurant_voting.repository.VoteRepository;
import ru.restaurant_voting.util.JsonUtil;
import ru.restaurant_voting.web.AbstractControllerTest;
import ru.restaurant_voting.web.restaurant.RestaurantTestData;
import ru.restaurant_voting.web.user.UserTestData;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminVoteControllerTest extends AbstractControllerTest {

    static final String REST_URL = "/api/admin/vote";

    @Autowired
    VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getAll() throws Exception {
        ResultActions actions = perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        List<Vote> expected = List.of(VoteTestData.USER_1_VOTE_FOR_RESTAURANT_1,
                VoteTestData.ADMIN_VOTE_FOR_RESTAURANT_1, VoteTestData.USER_2_VOTE_FOR_RESTAURANT_2);
        List<Vote> actual = JsonUtil.readValues(actions.andReturn().getResponse().getContentAsString(), Vote.class);
        actual.forEach(vote -> {
            RestaurantTestData.RESTAURANT_MATCHER.assertMatch(vote.getRestaurant(), expected.get(vote.getId() - 1).getRestaurant());
            UserTestData.USER_MATCHER.assertMatch(vote.getUser(), expected.get(vote.getId() - 1).getUser());
        });
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getByRestaurantId() throws Exception {
        ResultActions actions = perform(MockMvcRequestBuilders.get(REST_URL + "/rest=2"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        List<Vote> vote = JsonUtil.readValues(actions.andReturn().getResponse().getContentAsString(), Vote.class);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(vote.get(0).getRestaurant(), RestaurantTestData.restaurant_2);
        UserTestData.USER_MATCHER.assertMatch(vote.get(0).getUser(), UserTestData.user2);
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getByUserId() throws Exception {
        ResultActions actions = perform(MockMvcRequestBuilders.get(REST_URL + "/user=1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        Vote vote = VoteTestData.VOTE_MATCHER.readFromJson(actions);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(vote.getRestaurant(), RestaurantTestData.restaurant_1);
        UserTestData.USER_MATCHER.assertMatch(vote.getUser(), UserTestData.user);
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getByDate() throws Exception {
        Vote old = VoteTestData.getNew();
        ResultActions actions = perform(MockMvcRequestBuilders
                .get(REST_URL + "/date?dateTime=" + LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).minusDays(2L)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        List<Vote> actual = JsonUtil.readValues(actions.andReturn().getResponse().getContentAsString(), Vote.class);
        actual.forEach(vote -> {
            RestaurantTestData.RESTAURANT_MATCHER.assertMatch(vote.getRestaurant(), old.getRestaurant());
            UserTestData.USER_MATCHER.assertMatch(vote.getUser(), old.getUser());
        });
    }
}