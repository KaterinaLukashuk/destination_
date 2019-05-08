package com;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateDeserializer extends StdDeserializer<Date> {
    private static SimpleDateFormat formatter
            = new SimpleDateFormat("dd-MM-yyyy");

    public MyDateDeserializer() {
        this(null);
    }

    private MyDateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Date deserialize(JsonParser jsonParser,
                            DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            return formatter.parse(jsonParser.getText());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
