package com.yorix.autometer.service;

import com.yorix.autometer.errors.StorageException;
import com.yorix.autometer.model.Role;
import com.yorix.autometer.model.User;
import com.yorix.autometer.model.Visit;
import com.yorix.autometer.storage.UserRepository;
import com.yorix.autometer.storage.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService extends AppService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VisitRepository visitRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, VisitRepository visitRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.visitRepository = visitRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getByUsername(username);
    }

    public List<User> readAll() {
        return userRepository.findAll();
    }

    public void create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles() == null || user.getRoles().isEmpty())
            user.setRoles(Collections.singleton(Role.USER));
        String curImg = getAppProperties().getDefaultUserImageFilename();
        user.setCurrentImg(curImg);
        user.setActive(true);
        userRepository.save(user);
        saveData();
    }

    public void update(User user, Map<String, String> form, MultipartFile file) {
        String password = form.get("password");
        Set<Role> roles = form.keySet().stream()
                .filter(s -> Arrays.stream(Role.values()).map(Enum::name).toList().contains(s))
                .map(Role::valueOf)
                .collect(Collectors.toSet());

        user.setActive(form.get("active") != null);

        if (StringUtils.hasText(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        if (!roles.isEmpty()) {
            user.getRoles().clear();
            user.setRoles(roles);
        }
        saveFile(user, file);
        userRepository.save(user);
        saveData();
    }

    public void saveFile(User user, MultipartFile file) {
        if (file == null || !StringUtils.hasText(file.getOriginalFilename()))
            return;

        String filepath = getAppProperties().getImageStorageLocation()
                .concat("user")
                .concat(File.separator)
                .concat(Integer.toString(user.getId()))
                .concat(File.separator)
                .concat(file.getOriginalFilename());

        try {
            File dir = new File(filepath).getParentFile();
            dir.mkdirs();
            file.transferTo(new File(filepath));
        } catch (IOException e) {
            throw new StorageException("Ошибка загрузки файла " + file.getOriginalFilename(), e);
        }

        user.setCurrentImg("user/".concat(Integer.toString(user.getId())).concat("/").concat(file.getOriginalFilename()));
    }

    public void delete(User user) {
        userRepository.delete(user);
        saveData();
    }

    public void saveVisit(User user) {
        Visit visit = new Visit();
        visit.setTime(LocalDateTime.now());
        visit.setUser(user);
        visitRepository.save(visit);
    }

    public List<Visit> getVisits(LocalDateTime from, LocalDateTime to) {
        return visitRepository.findAllByTimeBetweenOrderByTimeDesc(from, to);
    }
}
