package ru.restaurant_voting.config.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.restaurant_voting.model.MenuItem;

import java.io.IOException;

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
        Integer id = Integer.parseInt(String.valueOf(jsonNode.get("id")));
        String name = String.valueOf(jsonNode.get("name")).replaceAll("\"", "");
        Integer price = Integer.parseInt(String.valueOf(jsonNode.get("price")));
        return new MenuItem(id, name, price);
    }
}
