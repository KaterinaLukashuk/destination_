package com.utils.jsonparser;

import com.model.data.Calculate;
import com.model.data.Holiday;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.utils.jsonparser.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class JsonUtilsTest {

    @org.junit.Test
    public void holidaysFromJSON() throws IOException, ClassNotFoundException {

        String json = "[{\"date\":\"2019-01-01\",\"class\":\"FULL\",\"name\":\"New Year's Day            (GL)\"}," +
                "{\"date\":\"2019-01-01\",\"class\":\"Predefined\",\"name\":null}," +
                "{\"date\":\"2019-01-02\",\"class\":\"Predefined\",\"name\":null}]";
        List<Holiday> holidays = JsonUtils.fromJSON(json, new TypeReference<List<Holiday>>() {
        });
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
        for (Holiday holiday : holidays) {
            System.out.println(holiday.getDate() + " " + holiday.getName() + " " + holiday.getClassH());
        }
    }

    @org.junit.Test
    public void holidayFromJSON() throws IOException {
        String json = "{\"date\":\"2019-01-01\",\"class\":\"FULL\",\"name\":\"New Year's Day (GL)\"}";
        Holiday holiday = (Holiday) JsonUtils.fromJSON(json, new TypeReference<Holiday>() {
        });
        System.out.println(holiday.getDate() + " " + holiday.getName() + " " + holiday.getClassH());


    }

    @org.junit.Test
    public void holidayToJSON() throws JsonProcessingException {
        Holiday holiday = new Holiday(LocalDate.now(), "name", "class");
        System.out.println(JsonUtils.toJSON(holiday));
    }

    @org.junit.Test
    public void calcFromJSON() throws IOException {
        Calculate calculate = JsonUtils.fromJSON("{\"companyCode\":\"0001\"," +
                        "\"submissionDate\":\"2019-04-18T08:40:17\"," +
                        "\"hearingDate\":\"2019-04-23\"," +
                        "\"deadline\":\"2019-04-30\"}",
                new TypeReference<Calculate>() {});
        System.out.println(calculate.getCompanyCode());
    }

    @org.junit.Test
    public void calcToJSON() throws JsonProcessingException {
        Calculate calculate = new Calculate(null,
                null,
                LocalDate.now(),
                null
                );
        System.out.println(JsonUtils.toJSON(calculate));
    }
    @org.junit.Test
    public void datesTest(){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-dd-MM");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy-dd-MM HH:mm");
        //System.out.println(timeFormatter.format(LocalDateTime.parse("2019-04-18T08:40:17")));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println(formatter.format(LocalDateTime.now()));
    }
}