package com.utils.jsonparser;

import com.model.data.Holiday;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParseJSON {
    public List<Holiday> parse(String json) throws JSONException, IOException {
        JSONArray arr = new JSONArray(json);
        List<Holiday> holidays = new ArrayList<>();
        Holiday holiday;
        for (int i = 0; i < arr.length(); i++) {
//            holiday = new Holiday();
//            holiday.setDate(Date.valueOf(arr.getJSONObject(i).getString("date")));
//            holiday.setClassH(arr.getJSONObject(i).getString("class"));
//            holiday.setName( arr.getJSONObject(i).get("name").toString());
        //   holiday = JsonUtils.holidayFromJSON(arr.getJSONObject(i).toString(), Holiday.class);
          //  holidays.add(holiday);
        }
        return holidays;
    }
}