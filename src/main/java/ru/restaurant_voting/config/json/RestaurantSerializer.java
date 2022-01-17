package ru.restaurant_voting.config.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.restaurant_voting.model.Restaurant;

import java.io.IOException;

public class RestaurantSerializer extends StdSerializer<Restaurant> {
    public RestaurantSerializer() {
        this(null);
    }

    public RestaurantSerializer(Class<Restaurant> t) {
        super(t);
    }

    @Override
    public void serialize(
            Restaurant value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        if (!(value.getId() == null)) {
            jgen.writeNumberField("id", value.getId());
        }
        jgen.writeStringField("name", value.getName());
        try {
            if (!(value.getMenu().isEmpty() || value.getMenu() == null)) {
                jgen.writeObjectField("menu", value.getMenu());
            }
        } catch (Exception ignored) {
        }
        jgen.writeEndObject();
    }
}
