package com.yorix.autometer.service;

import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.Img;
import com.yorix.autometer.model.Note;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarParser {
    private Document document;
    private Car car;
    private String make;
    private String model;
    private int year;
    private List<Note> notes;
    private List<Img> imgs;

    Car parse(String url) {
        car = new Car();

        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element div = document.body()
                .getElementById("modal-img")
                .getElementsByClass("modal-body").first();

        List<String> imgUrls = div.children().stream()
                .map(element -> element.attributes().get("src"))
                .collect(Collectors.toList());


        return car;
    }
}
