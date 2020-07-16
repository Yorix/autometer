package com.yorix.autometer.service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlBold;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
import com.yorix.autometer.config.AppProperties;
import com.yorix.autometer.model.Car;
import com.yorix.autometer.storage.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

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

    void parse(String vinOrLot, Car car) throws IOException {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

        final HtmlPage startPage = webClient.getPage(appProperties.getAuctionUrl().concat(vinOrLot).concat("/"));
        final HtmlAnchor a = startPage.getFirstByXPath("/html/body/div[2]/div/div/div/div[3]/div/div/div/table/tbody/tr/td[2]/ul/li[1]/a");
        if (a == null) throw new IOException("Авто не найдено, или неверный VIN/номер лота.");
        document = a.click();


        String make = ((HtmlTableDataCell) document
                .getFirstByXPath("/html/body/div[2]/div/div/div/div[4]/table/tbody/tr[13]/td[2]"))
                .getTextContent();
        String model = ((HtmlTableDataCell) document
                .getFirstByXPath("/html/body/div[2]/div/div/div/div[4]/table/tbody/tr[14]/td[2]"))
                .getTextContent();
        int year = Integer.parseInt(((HtmlTableDataCell) document
                .getFirstByXPath("/html/body/div[2]/div/div/div/div[4]/table/tbody/tr[16]/td[2]"))
                .getTextContent()
                .replaceAll("\\D", ""));
        int lot = Integer.parseInt(((HtmlTableDataCell) document
                .getFirstByXPath("/html/body/div[2]/div/div/div/div[4]/table/tbody/tr[2]/td[2]"))
                .getTextContent()
                .replaceAll("\\D", ""));
        String vin = ((HtmlBold) document
                .getFirstByXPath("/html/body/div[2]/div/div/div/div[4]/table/tbody/tr[4]/td[2]/b"))
                .getTextContent();
        int odometer = Integer.parseInt(((HtmlTableDataCell) document
                .getFirstByXPath("/html/body/div[2]/div/div/div/div[4]/table/tbody/tr[7]/td[2]"))
                .getTextContent()
                .replaceAll("\\D+.*", ""));
        double engine = Double.parseDouble(((HtmlTableDataCell) document
                .getFirstByXPath("/html/body/div[2]/div/div/div/div[4]/table/tbody/tr[21]/td[2]"))
                .getTextContent()
                .replaceAll("[^\\d.]+.*", ""));
        String fuel = ((HtmlTableDataCell) document
                .getFirstByXPath("/html/body/div[2]/div/div/div/div[4]/table/tbody/tr[25]/td[2]"))
                .getTextContent();
        String driveLine = ((HtmlTableDataCell) document
                .getFirstByXPath("/html/body/div[2]/div/div/div/div[4]/table/tbody/tr[23]/td[2]"))
                .getTextContent();
        String transmission = ((HtmlTableDataCell) document
                .getFirstByXPath("/html/body/div[2]/div/div/div/div[4]/table/tbody/tr[22]/td[2]"))
                .getTextContent();
        String color = ((HtmlTableDataCell) document
                .getFirstByXPath("/html/body/div[2]/div/div/div/div[4]/table/tbody/tr[18]/td[2]"))
                .getTextContent();
        String loss = "";
        String damage = ((HtmlTableDataCell) document
                .getFirstByXPath("/html/body/div[2]/div/div/div/div[4]/table/tbody/tr[5]/td[2]"))
                .getTextContent();
        String runAndDrive = "";
        String starts = ((HtmlTableDataCell) document
                .getFirstByXPath("/html/body/div[2]/div/div/div/div[4]/table/tbody/tr[8]/td[2]"))
                .getTextContent();
        String carKeys = ((HtmlTableDataCell) document
                .getFirstByXPath("/html/body/div[2]/div/div/div/div[4]/table/tbody/tr[9]/td[2]"))
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

//        String date = (((HtmlTableDataCell) document.getFirstByXPath("/html/body/div[2]/div/div/div/div[4]/table/tbody/tr[3]/td[2]")).getTextContent());
//        LocalDate auctionDate = LocalDate.parse(date);
//
//        double price = Double.parseDouble(((HtmlTableDataCell) document
//                .getFirstByXPath(""))
//                .getTextContent()
//                .replaceAll("[^\\d.]+.*", "")) * -1; //TODO
//
//        Note note = new Note();
//        note.setDescription("Покупка");
//        note.setValue(price);
//        note.setCar(car);
//        note.setDate(auctionDate);
//        noteService.create(note);
//
//
//        DomNodeList<DomNode> imgUrlList = document.querySelectorAll("#modal-img > div > div > div.modal-body > img");
//
//        List<String> imgUrls = imgUrlList.stream()
//                .map(element -> element.getAttributes().getNamedItem("src").getTextContent())
//                .collect(Collectors.toList());
//
//        imgUrls.forEach(url -> imgService.create(url, car));
    }
}
