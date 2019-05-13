package com.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.model.data.Calculate;
import com.model.data.Holiday;
import com.utils.jsonparser.JsonUtils;
import com.sap.ea.eacp.okhttp.destinationclient.OkHttpDestination;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import java.util.List;

@Service
public class HolidayService {

    @Autowired
    private OkHttpDestination tryDestination;


    public Response getResponse(String req) throws IOException {
        Request request = tryDestination.newRequest(req).get().build();
        OkHttpClient client = tryDestination.client();
        return client.newCall(request).execute();
    }

    public String getAllHolidays(Response response) throws IOException {
        List<Holiday> holidays = JsonUtils.fromJSON(response.body().string(),
                new TypeReference<List<Holiday>>() {
                });
        StringBuilder holidaysStr = new StringBuilder();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
        for (Holiday holiday : holidays) {
            holidaysStr.append(formatter.format(holiday.getDate())).
                    append(" ").append(holiday.getName()).
                    append(" ").append(holiday.getClassH()).
                    append("<br>");
        }
        return holidaysStr.toString();
    }

    public String calculate(Response response) throws IOException {
        Calculate calculate = JsonUtils.fromJSON(response.body().string(), new TypeReference<Calculate>() {
        });
        StringBuilder str = new StringBuilder();
        str.append("Company code: ").append(calculate.getCompanyCode()).append("<br>").
                append("Submission date: ").append(calculate.getSubmissionDate()).append("<br>").
                append("Hearing date: ").append(calculate.getHearingDate()).append("<br>").
                append("Deadline: ").append(calculate.getDeadline());
        return str.toString();
    }
    public String calculateJson(Calculate calculate) throws JsonProcessingException {
        return JsonUtils.toJSON(calculate);
    }
}
