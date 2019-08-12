package com.yorix.autometer.service;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URL;

@Service
public class CurrencyParser extends AppService {

    public double getRate(String currencyCode, String date) {
        String params = String.format(
                getProperties().getCurrencyUrlParams(),
                currencyCode,
                date
        );

        String path = getProperties().getCurrencyUrl().concat(params);

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
