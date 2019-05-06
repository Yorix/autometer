package com.yorix.autometer.service;

import com.yorix.autometer.config.AppProperties;
import com.yorix.autometer.model.*;
import com.yorix.autometer.storage.CarRepository;
import com.yorix.autometer.storage.ImgRepository;
import com.yorix.autometer.storage.NoteRepository;
import com.yorix.autometer.storage.ParamRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileSystemDataStorageService implements DataStorageService {
    private final CarRepository carRepository;
    private final NoteRepository noteRepository;
    private final ParamRepository paramRepository;
    private final ImgRepository imgRepository;
    private final JdbcTemplate jdbcTemplate;
    private final String rootLoc;
    private final String dbLoc;

    @Autowired
    public FileSystemDataStorageService(CarRepository carRepository,
                                        NoteRepository noteRepository,
                                        ParamRepository paramRepository,
                                        ImgRepository imgRepository,
                                        JdbcTemplate jdbcTemplate,
                                        AppProperties properties) {
        this.carRepository = carRepository;
        this.noteRepository = noteRepository;
        this.paramRepository = paramRepository;
        this.imgRepository = imgRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.rootLoc = properties.getRootLocation();
        this.dbLoc = properties.getDbBackupLocation();
    }

    @Override
    public void save() {
        List<CarJsonDTO> carsDTO = carRepository.findAll().stream().map(CarJsonDTO::new).collect(Collectors.toList());
        List<NoteJsonDTO> notesDTO = noteRepository.findAll().stream().map(NoteJsonDTO::new).collect(Collectors.toList());
        List<ImgJsonDTO> imgsDTO = imgRepository.findAll().stream().map(ImgJsonDTO::new).collect(Collectors.toList());

        JSONArray cars = new JSONArray(carsDTO);
        JSONArray notes = new JSONArray(notesDTO);
        JSONArray params = new JSONArray(paramRepository.findAll());
        JSONArray imgs = new JSONArray(imgsDTO);

        try (FileWriter carsWriter = new FileWriter(dbLoc + "cars.json");
             FileWriter notesWriter = new FileWriter(dbLoc + "notes.json");
             FileWriter paramsWriter = new FileWriter(dbLoc + "params.json");
             FileWriter imgsWriter = new FileWriter(dbLoc + "imgs.json")) {

            carsWriter.write(cars.toString());
            notesWriter.write(notes.toString());
            paramsWriter.write(params.toString());
            imgsWriter.write(imgs.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void read() {
        String initDB = getString("/scripts/initDB.sql");
        String carsStr = getString(dbLoc + "cars.json");
        String notesStr = getString(dbLoc + "notes.json");
        String paramsStr = getString(dbLoc + "params.json");
        String imgsStr = getString(dbLoc + "imgs.json");

        jdbcTemplate.execute(initDB);

        JSONArray jsonArray = new JSONArray(carsStr);
        for (int i = 0, length = jsonArray.length(); i < length; i++) {
            Car car = new Car();
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            car.setId(jsonObject.getInt("id"));
            car.setMake(jsonObject.getString("make"));
            car.setModel(jsonObject.getString("model"));
            car.setImgFilename(jsonObject.getString("imgFilename"));

            carRepository.save(car);
        }

        jsonArray = new JSONArray(notesStr);
        for (int i = 0, length = jsonArray.length(); i < length; i++) {
            Note note = new Note();
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            note.setId(jsonObject.getInt("id"));
            note.setDescription(jsonObject.getString("description"));
            note.setValue(jsonObject.getDouble("value"));
            note.setDate(jsonObject.getString("date"));
            note.setCar(carRepository.getOne(jsonObject.getInt("carId")));

            noteRepository.save(note);
        }

        jsonArray = new JSONArray(paramsStr);
        for (int i = 0, length = jsonArray.length(); i < length; i++) {
            Param param = new Param();
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            param.setName(jsonObject.getString("name"));
            param.setValue(jsonObject.getDouble("value"));

            paramRepository.save(param);
        }

        jsonArray = new JSONArray(imgsStr);
        for (int i = 0, length = jsonArray.length(); i < length; i++) {
            Img img = new Img();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            ImgJsonDTO imgJsonDTO = (ImgJsonDTO) jsonArray.get(i);
            System.out.println(imgJsonDTO.getFilename());
        }
    }

    private String getString(String path) {
        String text = "";
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {
            text = new String(inputStream.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
