package com;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ea.eacp.okhttp.destinationclient.OkHttpDestination;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
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
      //  ParseJSON parseJSON = new ParseJSON();
        String hollidaysStr = "";

          //  List<Holiday> holidays = parseJSON.parse(response.body().string());
            List<Holiday> holidays = JsonUtils.holidaysFromJSON(response.body().string(), new TypeReference<List<Holiday>>(){});
            for (int i = 0; i < holidays.size(); i++)
            {
                hollidaysStr += holidays.get(i).getDate() + " " +
                        holidays.get(i).getName() + " " +
                        holidays.get(i).getClassH() + "<br>";
            }

        return ResponseEntity.ok(hollidaysStr);
    }

}
