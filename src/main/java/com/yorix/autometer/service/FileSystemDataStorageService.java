package com.yorix.autometer.service;

import com.yorix.autometer.config.AppProperties;
import com.yorix.autometer.model.CarJsonDTO;
import com.yorix.autometer.model.ImgJsonDTO;
import com.yorix.autometer.model.NoteJsonDTO;
import com.yorix.autometer.storage.CarRepository;
import com.yorix.autometer.storage.ImgRepository;
import com.yorix.autometer.storage.NoteRepository;
import com.yorix.autometer.storage.ParamRepository;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
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
    private final ImageStorageService imageStorageService;
    private final String dbLoc;

    @Autowired
    public FileSystemDataStorageService(CarRepository carRepository,
                                        NoteRepository noteRepository,
                                        ParamRepository paramRepository,
                                        ImgRepository imgRepository,
                                        ImageStorageService imageStorageService,
                                        AppProperties properties) {
        this.carRepository = carRepository;
        this.noteRepository = noteRepository;
        this.paramRepository = paramRepository;
        this.imgRepository = imgRepository;
        this.imageStorageService = imageStorageService;
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

    }
}
