package com.yorix.autometer.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
public class CurrencyParser extends AppService {

    public double getRate(String currencyCode, String date) {
        String params = String.format(
                getAppProperties().getCurrencyUrlParams(),
                currencyCode,
                date
        );

        String path = getAppProperties().getCurrencyUrl().concat(params);

        double rate = 0;
        try {
            URL url = new URL(path);
            String response = new String(url.openStream().readAllBytes());
            JSONObject jsonObject = new JSONArray(response).getJSONObject(0);
            rate = jsonObject.getDouble("rate");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return rate;
    }
}
