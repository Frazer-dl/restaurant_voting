package ru.restaurant_voting.config.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.restaurant_voting.model.MenuItem;
import ru.restaurant_voting.model.Restaurant;

import java.io.IOException;
import java.time.LocalDate;

public class MenuDeserializer extends StdDeserializer<MenuItem> {

    public MenuDeserializer() {
        this(null);
    }

    public MenuDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MenuItem deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode jsonNode = jp.getCodec().readTree(jp);
        Integer id;
        try {
            id = Integer.parseInt(String.valueOf(jsonNode.get("id")));
        } catch (Exception e) {
            id = null;
        }
        String name = String.valueOf(jsonNode.get("name")).replaceAll("\"", "");
        Integer price = Integer.parseInt(String.valueOf(jsonNode.get("price")));
        return new MenuItem(id, name, LocalDate.now(), price, new Restaurant(null, null, null));
    }
}
