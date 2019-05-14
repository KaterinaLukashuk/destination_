package com.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.model.data.Calculate;
import com.model.data.Holiday;
import com.utils.jsonparser.JsonUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class CalculateServiceTest {


    @Test
    public void calculateDeadline() throws IOException {
        String json = "[{\"date\":\"2019-01-01\",\"class\":\"Predefined\",\"name\":null},{\"date\":\"2019-01-01\",\"class\":\"FULL\",\"name\":\"New Year's Day (GL)\"},\n" +
                "{\"date\":\"2019-01-02\",\"class\":\"Predefined\",\"name\":null},{\"date\":\"2019-01-06\",\"class\":\"FULL\",\"name\":\"Epiphany (GL)\"},\n" +
                "{\"date\":\"2019-04-19\",\"class\":\"FULL\",\"name\":\"Good Friday\"},{\"date\":\"2019-04-21\",\"class\":\"FULL\",\"name\":\"EasterSunday (DayType3!) (DE)\"},\n" +
                "{\"date\":\"2019-04-22\",\"class\":\"FULL\",\"name\":\"Easter Monday (GL)\"},{\"date\":\"2019-05-01\",\"class\":\"FULL\",\"name\":\"May/Labour Day (GL)\"},\n" +
                "{\"date\":\"2019-05-30\",\"class\":\"FULL\",\"name\":\"Ascension Day (GL)\"},{\"date\":\"2019-06-09\",\"class\":\"FULL\",\"name\":\"Whit Sunday (Daytype3) (DE)\"},\n" +
                "{\"date\":\"2019-06-10\",\"class\":\"FULL\",\"name\":\"Whit Monday (GL)\"},{\"date\":\"2019-06-20\",\"class\":\"FULL\",\"name\":\"Corpus Christi\"},\n" +
                "{\"date\":\"2019-10-03\",\"class\":\"FULL\",\"name\":\"D: Unification Day (ab 1990)\"},{\"date\":\"2019-11-01\",\"class\":\"FULL\",\"name\":\"All Saints' Day (GL)\"},\n" +
                "{\"date\":\"2019-12-24\",\"class\":\"HALF\",\"name\":\"Christmas Eve (half day) (GL)\"},{\"date\":\"2019-12-25\",\"class\":\"FULL\",\"name\":\"Christmas Day (GL)\"},\n" +
                "{\"date\":\"2019-12-26\",\"class\":\"FULL\",\"name\":\"Boxing Day (GL)\"},{\"date\":\"2019-12-31\",\"class\":\"HALF\",\"name\":\"New Year's Eve (half day) (GL)\"},\n" +
                "{\"date\":\"2020-01-01\",\"class\":\"FULL\",\"name\":\"New Year's Day (GL)\"},{\"date\":\"2020-01-06\",\"class\":\"FULL\",\"name\":\"Epiphany (GL)\"},\n" +
                "{\"date\":\"2020-04-10\",\"class\":\"FULL\",\"name\":\"Good Friday\"},{\"date\":\"2020-04-12\",\"class\":\"FULL\",\"name\":\"EasterSunday (DayType3!) (DE)\"},\n" +
                "{\"date\":\"2020-04-13\",\"class\":\"FULL\",\"name\":\"Easter Monday (GL)\"},{\"date\":\"2020-05-01\",\"class\":\"FULL\",\"name\":\"May/Labour Day (GL)\"},\n" +
                "{\"date\":\"2020-05-21\",\"class\":\"FULL\",\"name\":\"Ascension Day (GL)\"},{\"date\":\"2020-05-31\",\"class\":\"FULL\",\"name\":\"Whit Sunday (Daytype3) (DE)\"},\n" +
                "{\"date\":\"2020-06-01\",\"class\":\"FULL\",\"name\":\"Whit Monday (GL)\"},{\"date\":\"2020-06-11\",\"class\":\"FULL\",\"name\":\"Corpus Christi\"},\n" +
                "{\"date\":\"2020-10-03\",\"class\":\"FULL\",\"name\":\"D: Unification Day (ab 1990)\"},{\"date\":\"2020-11-01\",\"class\":\"FULL\",\"name\":\"All Saints' Day (GL)\"},\n" +
                "{\"date\":\"2020-12-24\",\"class\":\"HALF\",\"name\":\"Christmas Eve (half day) (GL)\"},{\"date\":\"2020-12-25\",\"class\":\"FULL\",\"name\":\"Christmas Day (GL)\"},\n" +
                "{\"date\":\"2020-12-26\",\"class\":\"FULL\",\"name\":\"Boxing Day (GL)\"},{\"date\":\"2020-12-31\",\"class\":\"HALF\",\"name\":\"New Year's Eve (half day) (GL)\"}]";
        List<Holiday> holidays = JsonUtils.fromJSON(json, new TypeReference<List<Holiday>>() {
        });
        CalculateService calculateService = new CalculateService();
        Calculate calculate = calculateService.calculateDeadline(LocalDate.of(2020,6,1), holidays, "0023");
        System.out.println(calculate);
        System.out.println("holidays: " + calculateService.holidaysAmount(LocalDate.of(2020,6,1),calculate.getDeadline(), holidays));
        System.out.println("weekends: " + calculateService.weekendsAmount(LocalDate.of(2020,6,1),calculate.getDeadline()));
        System.out.println(calculateService.calculateDeadline(LocalDate.of(2020,5,21), holidays, "0023"));
//        calculateService.calculateDeadline(LocalDate.now(), holidays, "002");
    }

    @Test
    public void isHoliday() throws IOException {
        String json = "[{\"date\":\"2019-01-01\",\"class\":\"FULL\",\"name\":\"New Year's Day            (GL)\"}," +
                "{\"date\":\"2019-05-15\",\"class\":\"Predefined\",\"name\":null}," +
                "{\"date\":\"2019-05-14\",\"class\":\"Predefined\",\"name\":null}]";
        List<Holiday> holidays = JsonUtils.fromJSON(json, new TypeReference<List<Holiday>>() {
        });
        CalculateService calculateService = new CalculateService();
        //System.out.println(calculateService.isHoliday(holidays,LocalDate.now()));
    }
}