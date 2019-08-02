package com.yorix.autometer.service;

import com.yorix.autometer.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URL;

@Service
public class CurrencyParser {
    private final AppProperties properties;

    @Autowired
    public CurrencyParser(AppProperties properties) {
        this.properties = properties;
    }

    public double getRate(String... paramValues) {
        double rate = 0;
        String params = "";
        if (!StringUtils.isEmpty(properties.getCurrencyUrlParams())) {
            params = properties.getCurrencyUrlParams();
            for (int i = 0, length = paramValues.length; i < length; i++) {
                params = params.replaceAll("\\{param" + i + "}", paramValues[i]);
            }
        }

        String path = properties.getCurrencyUrl().concat(params);

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
