package com.utils.jsonparser;

import com.model.data.Calculate;
import com.model.data.Holiday;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class JsonUtilsTest {

    @Test
    public void holidaysFromJSON() throws IOException {

        String json = "[{\"date\":\"2019-01-01\",\"class\":\"FULL\",\"name\":\"New Year's Day(GL)\"}," +
                "{\"date\":\"2019-01-01\",\"class\":\"Predefined\",\"name\":null}," +
                "{\"date\":\"2019-01-02\",\"class\":\"Predefined\",\"name\":null}]";
       List<Holiday> holidays = JsonUtils.fromJSON(json, new TypeReference<List<Holiday>>() {
        });
        List<Holiday> excepted = new ArrayList<>();
        excepted.add(new Holiday(LocalDate.of(2019,1,1),"New Year's Day(GL)", "FULL"));
        excepted.add(new Holiday(LocalDate.of(2019,1,1), null,"Predefined"));
        excepted.add(new Holiday(LocalDate.of(2019,1,2),null, "Predefined"));
      //  assertArrayEquals(excepted.toArray(),holidays.toArray());
        assertThat(excepted.toArray(),is(holidays.toArray()));
    }

    @Test
    public void holidayFromJSON() throws IOException {
        String json = "{\"date\":\"2019-01-01\",\"class\":\"FULL\",\"name\":\"New Year's Day (GL)\"}";
        Holiday holiday =  JsonUtils.fromJSON(json, new TypeReference<Holiday>() {
        });
        Holiday excepted = new Holiday(LocalDate.of(2019,1,1),"New Year's Day (GL)","FULL");
        assertThat(excepted,is(holiday));
    }

    @Test
    public void holidayToJSON() throws JsonProcessingException {
        Holiday holiday = new Holiday(LocalDate.now(), "name", "class");
        String excepted = "{\"Holiday\":{\"date\":[2019,6,11],\"name\":\"name\",\"class\":\"class\"}}";
        assertThat(excepted,is(JsonUtils.toJSON(holiday)));
    }

    @Test
    public void calcFromJSON() throws IOException {
        Calculate calculate = JsonUtils.fromJSON("{\"companyCode\":\"0001\"," +
                        "\"submissionDate\":\"2019-04-18T08:40:17\"," +
                        "\"hearingDate\":\"2019-04-23\"," +
                        "\"deadline\":\"2019-04-30\"}",
                new TypeReference<Calculate>() {});
        Calculate excepted = new Calculate("0001",
                LocalDateTime.of(2019,4,18,8,40,17),
                LocalDate.of(2019,4,23),
                LocalDate.of(2019,4,30));
        assertThat(excepted,is(calculate));
    }

    @Test
    public void calcToJSON() throws JsonProcessingException {
        Calculate calculate = new Calculate(null,
                null,
                LocalDate.of(2019,6,7),
                null
                );
        String excepted = "{\"Calculate\":{\"hearingDate\":[2019,6,7]}}";
        assertThat(excepted,is(JsonUtils.toJSON(calculate)));
    }

    @Test(expected = JsonProcessingException.class)
    public void calcFromJSONException() throws IOException {
        JsonUtils.fromJSON("", new TypeReference<Holiday>() {
        });
    }
}