package com.utils.jsonparser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class JsonUtils {
    private static void mapperConfig(ObjectMapper objectMapper) {
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
    }

    public static <T> T fromJSON(String json, TypeReference<T> valueType) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, valueType);
    }

    public static String toJSON(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        mapperConfig(objectMapper);
        return objectMapper.writeValueAsString(obj);
    }
}
