package ru.restaurant_voting.config.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.restaurant_voting.model.MenuItem;
import ru.restaurant_voting.model.Restaurant;

import java.io.IOException;
import java.util.List;

public class RestaurantDeserializer extends StdDeserializer<Restaurant> {

    public RestaurantDeserializer() {
        this(null);
    }

    public RestaurantDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Restaurant deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode jsonNode = jp.getCodec().readTree(jp);
        Integer id;
        try {
            id = Integer.parseInt(String.valueOf(jsonNode.get("id")));
        } catch (Exception e) {
            id = null;
        }
        String name = jsonNode.get("name").asText();
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType customClassCollection = objectMapper.getTypeFactory().constructCollectionType(List.class, MenuItem.class);
        List<MenuItem> beanList = null;
        try {
            beanList = objectMapper.readValue(String.valueOf(jsonNode.get("menu")),
                    customClassCollection); //https://stackoverflow.com/a/34179679/17483527
        } catch (Exception ignored) {

        }
        return new Restaurant(id, name, beanList);
    }
}
