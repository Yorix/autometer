package com.yorix.autometer.service;

import com.yorix.autometer.config.AppProperties;
import com.yorix.autometer.model.Note;
import com.yorix.autometer.storage.NoteRepository;
import com.yorix.autometer.storage.ParamRepository;
import com.yorix.autometer.storage.SpareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public abstract class AppService {
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private SpareRepository spareRepository;
    @Autowired
    private ParamRepository paramRepository;
    @Autowired
    private AppProperties appProperties;
    @Autowired
    private DataSourceProperties dataSourceProperties;
    @Autowired
    private MailService mailService;

    public double getBalance() {
        double notesBalance = noteRepository.findAll().stream()
                .mapToDouble(Note::getValue)
                .sum();
        double sparesBalance = spareRepository.findAll().stream()
                .mapToDouble(spare -> spare.getBuy() + spare.getSale())
                .sum();
        return notesBalance + sparesBalance;
    }

    public double getBudget() {
        return paramRepository.getOne("budget").getValue();
    }

    AppProperties getAppProperties() {
        return appProperties;
    }

    public void saveData() {
        String filename = appProperties.getDbBackupLocation() + "/autometer_"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern(appProperties.getDbFilenameTimeFormat()))
                + ".sql";
        String[] command = {
                appProperties.getShell(),
                appProperties.getShellArg(),
                "mysqldump -u" + dataSourceProperties.getUsername() + " -p" + dataSourceProperties.getPassword()
                        + " autometer > " + filename
        };

        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File backupDir = new File(appProperties.getDbBackupLocation());
        File[] files = backupDir.listFiles();
        if ((files != null ? files.length : 0) > 500) {
            files[0].delete();
        }

        try {
            mailService.send(new File(filename));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
