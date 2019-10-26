package com.yorix.autometer.service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.yorix.autometer.config.AppProperties;
import com.yorix.autometer.model.Car;
import com.yorix.autometer.model.Note;
import com.yorix.autometer.storage.CarRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarParser {
    private HtmlPage document;

    private final CarRepository carRepository;
    private final NoteService noteService;
    private final ImgService imgService;
    private final AppProperties appProperties;

    @Autowired
    CarParser(
            CarRepository carRepository,
            NoteService noteService,
            ImgService imgService,
            AppProperties appProperties
    ) {
        this.carRepository = carRepository;
        this.noteService = noteService;
        this.imgService = imgService;
        this.appProperties = appProperties;
    }

    void parse(String vinOrLot, Car car) throws Exception {
        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(true);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

            final HtmlPage startPage = webClient.getPage(appProperties.getAuctionUrl());
            final HtmlForm form = startPage.getFormByName("search_lot_by_identifier_form");
            final HtmlButton button = form.getButtonByName("search_lot_by_identifier_form[search]");
            final HtmlTextInput textField = form.getInputByName("search_lot_by_identifier_form[field]");
            textField.type(vinOrLot);

            document = button.click();
        } catch (IOException e) {
            e.printStackTrace();
        }

        DomNode infoScriptTag = document.querySelector("body > script:nth-child(8)");
        if (infoScriptTag == null)
            throw new Exception("Авто не найдено, или неверный VIN/номер лота.");

        String infoJson = infoScriptTag.getTextContent()
                .replaceAll("^[\\s\\S]*\\{", "{")
                .replaceAll("\\)$", "");

        JSONObject carInfo = new JSONObject(infoJson);

        String make = carInfo.getString("Make");
        String model = carInfo.getString("Model");
        int year = Integer.parseInt(carInfo.getString("Year"));
        int lot = Integer.parseInt(document
                .querySelector("body > div.page > div > div > div:nth-child(2) > div:nth-child(2) > div > div.card-body > div.row > div > dl > dd:nth-child(2)")
                .getTextContent());
        String vin = document
                .querySelector("body > div.page > div > div > div:nth-child(2) > div:nth-child(2) > div > div.card-body > div.row > div > dl > dd:nth-child(4)")
                .getTextContent();
        int odometer = Integer.parseInt(document
                .querySelector("body > div.page > div > div > div:nth-child(3) > div:nth-child(1) > div > div.card-body > dl > dd:nth-child(2)")
                .getTextContent()
                .replaceAll("\\D", ""));
        double engine = Double.parseDouble(document
                .querySelector("body > div.page > div > div > div:nth-child(3) > div:nth-child(1) > div > div.card-body > dl > dd:nth-child(4)")
                .getTextContent()
                .replaceAll("[^\\d.]", "")
        );
        String fuel = document
                .querySelector("body > div.page > div > div > div:nth-child(3) > div:nth-child(1) > div > div.card-body > dl > dd:nth-child(6)")
                .getTextContent();
        String driveLine = document
                .querySelector("body > div.page > div > div > div:nth-child(3) > div:nth-child(1) > div > div.card-body > dl > dd:nth-child(8)")
                .getTextContent();
        String transmission = document
                .querySelector("body > div.page > div > div > div:nth-child(3) > div:nth-child(1) > div > div.card-body > dl > dd:nth-child(10)")
                .getTextContent();
        String color = document
                .querySelector("body > div.page > div > div > div:nth-child(3) > div:nth-child(1) > div > div.card-body > dl > dd:nth-child(12)")
                .getTextContent();
        String loss = document
                .querySelector("body > div.page > div > div > div:nth-child(3) > div:nth-child(2) > div > div.card-body > dl > dd:nth-child(2)")
                .getTextContent();
        String damage = document
                .querySelector("body > div.page > div > div > div:nth-child(3) > div:nth-child(2) > div > div.card-body > dl > dd:nth-child(4)")
                .getTextContent();
        String runAndDrive = document
                .querySelector("body > div.page > div > div > div:nth-child(3) > div:nth-child(2) > div > div.card-body > dl > dd:nth-child(6)")
                .getTextContent();
        String starts = document
                .querySelector("body > div.page > div > div > div:nth-child(3) > div:nth-child(2) > div > div.card-body > dl > dd:nth-child(8)")
                .getTextContent();
        String carKeys = document
                .querySelector("body > div.page > div > div > div:nth-child(3) > div:nth-child(2) > div > div.card-body > dl > dd:nth-child(10)")
                .getTextContent();

        String currentImg = appProperties.getDefaultImageFilename();

        car.setMake(make);
        car.setModel(model);
        car.setYear(year);
        car.setLot(lot);
        car.setVin(vin);
        car.setOdometer(odometer);
        car.setEngine(engine);
        car.setFuel(fuel);
        car.setDriveLine(driveLine);
        car.setTransmission(transmission);
        car.setColor(color);
        car.setLoss(loss);
        car.setDamage(damage);
        car.setRunAndDrive(runAndDrive);
        car.setStarts(starts);
        car.setCarKeys(carKeys);
        car.setCurrentImg(currentImg);
        carRepository.save(car);


        LocalDate auctionDate = LocalDate.parse(carInfo.getString("Auction Date"));

        double price = Double.parseDouble(document
                .querySelector("body > div.page > div > div > div:nth-child(2) > div:nth-child(2) > div > div.card-body > div.table-responsive > table > tbody > tr:nth-child(1) > td:nth-child(3)")
                .getTextContent()
                .replaceAll("\\D", "")
        ) * -1;

        Note note = new Note();
        note.setDescription("Покупка");
        note.setValue(price);
        note.setCar(car);
        note.setDate(auctionDate);
        noteService.create(note);


        DomNodeList<DomNode> imgUrlList = document.querySelectorAll("#modal-img > div > div > div.modal-body > img");

        List<String> imgUrls = imgUrlList.stream()
                .map(element -> element.getAttributes().getNamedItem("src").getTextContent())
                .collect(Collectors.toList());

        imgUrls.forEach(url -> imgService.create(url, car));
    }
}
