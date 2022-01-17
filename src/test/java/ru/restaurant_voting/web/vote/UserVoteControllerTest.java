package ru.restaurant_voting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.restaurant_voting.model.Restaurant;
import ru.restaurant_voting.model.User;
import ru.restaurant_voting.model.Vote;
import ru.restaurant_voting.repository.RestaurantRepository;
import ru.restaurant_voting.repository.UserRepository;
import ru.restaurant_voting.repository.VoteRepository;
import ru.restaurant_voting.util.DateTimeUtil;
import ru.restaurant_voting.web.AbstractControllerTest;
import ru.restaurant_voting.web.restaurant.RestaurantTestData;
import ru.restaurant_voting.web.user.UserTestData;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserVoteControllerTest extends AbstractControllerTest {

    static final String REST_URL = "/api/profile/votes";

    @Autowired
    VoteRepository voteRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    @WithUserDetails(value = UserTestData.USER_2_MAIL)
    void create() throws Exception {
        voteRepository.delete(VoteTestData.VOTE_3_ID);
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL + "?restaurantId=1"))
                .andExpect(status().is2xxSuccessful())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        Vote vote = deserializer(actions);
        RestaurantTestData.RESTAURANT_MATCHER.assertMatch(vote.getRestaurant(),
                VoteTestData.USER_2_VOTE_FOR_RESTAURANT_1.getRestaurant());
        UserTestData.USER_MATCHER.assertMatch(vote.getUser(), VoteTestData.USER_2_VOTE_FOR_RESTAURANT_1.getUser());
    }

    @Test
    @WithUserDetails(value = UserTestData.USER_MAIL)
    void update() throws Exception {
        boolean inTime = DateTimeUtil.isUserVoteInTime(LocalDate.now());
        ResultActions actions = perform(MockMvcRequestBuilders.post(REST_URL + "?restaurantId=2"))
                .andExpect(inTime ? status().is2xxSuccessful() : status().is4xxClientError())
                .andDo(print())
                .andExpect(inTime ?
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON) :
                        VoteTestData.VOTE_MATCHER.contentJson(VoteTestData.ILLEGAL_VOTE));
        if (inTime) {
            Vote vote = deserializer(actions);
            RestaurantTestData.RESTAURANT_MATCHER.assertMatch(vote.getRestaurant(), VoteTestData.USER_1_VOTE_FOR_RESTAURANT_2.getRestaurant());
            UserTestData.USER_MATCHER.assertMatch(vote.getUser(), VoteTestData.USER_1_VOTE_FOR_RESTAURANT_2.getUser());
        }
    }

    Vote deserializer(ResultActions actions) throws UnsupportedEncodingException {
        Vote deserialized = VoteTestData.VOTE_MATCHER.readFromJson(actions);
        User user = userRepository.getById(deserialized.getUser().getId());
        Restaurant restaurant = new Restaurant(restaurantRepository.getById(deserialized.getRestaurant().getId()));
        restaurant.setId(deserialized.getRestaurant().getId());
        restaurant.setName(deserialized.getRestaurant().getName());
        return new Vote(deserialized.getId(), user, restaurant, deserialized.getDate());
    }
}