package com.model.jsonparser;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateSerializer extends StdSerializer<Date> {
    private static SimpleDateFormat formatter
            = new SimpleDateFormat("yyyy-dd-MM");

    public MyDateSerializer() {
        this(null);
    }

    protected MyDateSerializer(Class<Date> date) {
        super(date);
    }

    @Override
    public void serialize(Date date,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(formatter.format(date));
    }
}
