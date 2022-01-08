package ru.restaurant_voting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.restaurant_voting.repository.VoteRepository;
import ru.restaurant_voting.web.AbstractControllerTest;
import ru.restaurant_voting.web.user.UserTestData;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTE_MATCHER.contentJson(VoteTestData.USER_1_VOTE_FOR_RESTAURANT_1,
                        VoteTestData.USER_2_VOTE_FOR_RESTAURANT_1, VoteTestData.USER_3_VOTE_FOR_RESTAURANT_2));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getByRestaurantId() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/rest=1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTE_MATCHER.contentJson(VoteTestData.USER_1_VOTE_FOR_RESTAURANT_1,
                        VoteTestData.USER_2_VOTE_FOR_RESTAURANT_1));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getByUserId() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/user=1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTE_MATCHER.contentJson(VoteTestData.USER_1_VOTE_FOR_RESTAURANT_1));
    }

    @Test
    @WithUserDetails(value = UserTestData.ADMIN_MAIL)
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/date?dateTime=" + LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VoteTestData.VOTE_MATCHER.contentJson(VoteTestData.USER_1_VOTE_FOR_RESTAURANT_1,
                        VoteTestData.USER_2_VOTE_FOR_RESTAURANT_1, VoteTestData.USER_3_VOTE_FOR_RESTAURANT_2));
    }
}