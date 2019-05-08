package com;

import com.model.data.Holiday;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.model.jsonparser.JsonUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class JsonUtilsTest {

    @org.junit.Test
    public void holidaysFromJSON() throws IOException, ClassNotFoundException {

        String json = "[{\"date\":\"2019-01-01\",\"class\":\"FULL\",\"name\":\"New Year's Day            (GL)\"}," +
                "{\"date\":\"2019-01-01\",\"class\":\"Predefined\",\"name\":null}," +
                "{\"date\":\"2019-01-02\",\"class\":\"Predefined\",\"name\":null}]";
        List<Holiday> holidays = (List<Holiday>) JsonUtils.holidayFromJSON(json,  new TypeReference<List<Holiday>>() { });
        for (Holiday holiday: holidays)
        System.out.println(holiday.getDate()+ " " + holiday.getName() + " " + holiday.getClassH());
    }

    @org.junit.Test
    public void holidayFromJSON() throws IOException {
        String json = "{\"date\":\"2019-01-01\",\"class\":\"FULL\",\"name\":\"New Year's Day (GL)\"}";
        Holiday holiday = (Holiday) JsonUtils.holidayFromJSON(json,new TypeReference<Holiday>() { });
        System.out.println(holiday.getDate() + " " + holiday.getName() + " " + holiday.getClassH());

    }

    @org.junit.Test
    public void holidayToJSON() throws JsonProcessingException {
        Holiday holiday = new Holiday(new Date(),"name","class");
        System.out.println(JsonUtils.holidayToJSON(holiday));
    }
}