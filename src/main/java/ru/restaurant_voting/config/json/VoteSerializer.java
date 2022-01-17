package ru.restaurant_voting.config.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.restaurant_voting.model.Vote;

import java.io.IOException;

public class VoteSerializer extends StdSerializer<Vote> {
    public VoteSerializer() {
        this(null);
    }

    public VoteSerializer(Class<Vote> t) {
        super(t);
    }

    @Override
    public void serialize(
            Vote value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("user-id", value.getUser().getId().toString());
        jgen.writeStringField("user-email", value.getUser().getEmail());
        jgen.writeStringField("restaurant-id", value.getRestaurant().getId().toString());
        jgen.writeStringField("restaurant-name", value.getRestaurant().getName());
        jgen.writeStringField("date", value.getDate().toString());
        jgen.writeEndObject();
    }
}
