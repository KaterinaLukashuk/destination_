package com.controller;

import com.model.destination.DestinationFactory;
import com.model.jsonparser.JsonUtils;
import com.model.data.Holiday;
import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ea.eacp.okhttp.destinationclient.OkHttpDestination;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api")
public class HelloController {
    @GetMapping
    public ResponseEntity index() throws IOException {
        DestinationFactory destinationFactory = new DestinationFactory();
        // https://hearingdatecalculatora2ae72c92.hana.ondemand.com/api/holidays/all
        OkHttpDestination tryDestination = destinationFactory.provide("try");
        OkHttpClient client = tryDestination.client();
        Request.Builder builder = tryDestination.newRequest("/holidays/all").get();
        Request request = builder.build();
        Response response = client.newCall(request).execute();
        String holidaysStr = "";
            List<Holiday> holidays = (List<Holiday>) JsonUtils.holidayFromJSON(response.body().string(), new TypeReference<List<Holiday>>(){});
            for (int i = 0; i < holidays.size(); i++)
            {
                holidaysStr += holidays.get(i).getDate() + " " +
                        holidays.get(i).getName() + " " +
                        holidays.get(i).getClassH() + "<br>";
            }

        return ResponseEntity.ok(holidaysStr);
    }

}
