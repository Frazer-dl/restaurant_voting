package ru.restaurant_voting.config.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.restaurant_voting.model.MenuItem;

import java.io.IOException;

public class MenuSerializer extends StdSerializer<MenuItem> {
    public MenuSerializer() {
        this(null);
    }

    public MenuSerializer(Class<MenuItem> t) {
        super(t);
    }

    @Override
    public void serialize(
            MenuItem value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("name", value.getName());
        jgen.writeNumberField("price", value.getPrice());
        jgen.writeEndObject();
    }
}
