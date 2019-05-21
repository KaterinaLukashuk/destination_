package com.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.model.data.Holiday;
import com.utils.jsonparser.JsonUtils;
import com.sap.ea.eacp.okhttp.destinationclient.OkHttpDestination;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


import java.util.List;

@Service
public class HolidayService {

    private final OkHttpDestination tryDestination;

    @Autowired
    public HolidayService(OkHttpDestination tryDestination) {
        this.tryDestination = tryDestination;
    }


    public Response getResponse(String req) throws IOException, RuntimeException {
        Request request = tryDestination.newRequest(req).get().build();
        OkHttpClient client = tryDestination.client();
        return client.newCall(request).execute();
    }

    public String getAllHolidays(Response response) throws IOException {
        List<Holiday> holidays = JsonUtils.fromJSON(response.body().string(),
                new TypeReference<List<Holiday>>() {
                });
        StringBuilder holidaysStr = new StringBuilder();
        for (Holiday holiday : holidays) {
            holidaysStr.append(holiday.getDate()).
                    append(" ").append(holiday.getName()).
                    append(" ").append(holiday.getClassH()).
                    append("<br>");
        }
        return holidaysStr.toString();
    }

    public List<Holiday> getHolidaysList(Response response) throws IOException {
        return JsonUtils.fromJSON(response.body().string(),
                new TypeReference<List<Holiday>>() {
                });
    }


}
