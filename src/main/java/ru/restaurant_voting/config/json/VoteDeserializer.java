package ru.restaurant_voting.config.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.restaurant_voting.error.IllegalRequestDataException;
import ru.restaurant_voting.model.Restaurant;
import ru.restaurant_voting.model.Role;
import ru.restaurant_voting.model.User;
import ru.restaurant_voting.model.Vote;

import java.io.IOException;
import java.time.LocalDate;

public class VoteDeserializer extends StdDeserializer<Vote> {

    public VoteDeserializer() {
        this(null);
    }

    public VoteDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Vote deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        try {
            JsonNode node = jp.getCodec().readTree(jp);
            Integer voteId = Integer.parseInt(node.get("id").asText());
            Integer userId = Integer.parseInt(node.get("user-id").asText());
            Integer restaurantId = Integer.parseInt(node.get("restaurant-id").asText());
            LocalDate date = LocalDate.parse(node.get("date").asText());
            return new Vote(voteId, new User(userId, "User", "user@mail.ru", "password", Role.USER),
                    new Restaurant(restaurantId, "restaurantName", null), date);
        } catch (Exception e) {
            throw new IllegalRequestDataException("Vote body is incorrect.");
        }
    }
}
