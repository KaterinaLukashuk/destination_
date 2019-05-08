package com;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;

public class JsonUtils {
    public static Holiday holidayFromJSON(String json, Class<Holiday> valueType) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, valueType);
    }
    public static List<Holiday> holidaysFromJSON(String json, TypeReference<List<Holiday>> valueType) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, valueType);
    }
    public static String holidayToJSON(Holiday holiday) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(holiday);
    }
//    public static String holidaysToJSON(List<Holiday> holidays) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.writeValueAsString(holidays);
//    }
}
