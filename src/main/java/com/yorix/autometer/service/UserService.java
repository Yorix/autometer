package com.yorix.autometer.service;

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

    public User getUser(int id) {
        return userRepository.getOne(id);
    }

    public List<User> readAll() {
        return userRepository.findAll();
    }

    public void create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles() == null || user.getRoles().isEmpty())
            user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
    }

    public void update(User user, Map<String, String> form) {
        String password = form.get("password");
        Set<Role> roles = form.keySet().stream()
                .filter(s -> Arrays.stream(Role.values()).map(Enum::name).collect(Collectors.toList()).contains(s))
                .map(Role::valueOf)
                .collect(Collectors.toSet());

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        if (!roles.isEmpty()) {
            user.getRoles().clear();
            user.setRoles(roles);
        }
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void saveVisit(User user) {
        Visit visit = new Visit();
        visit.setTime(LocalDateTime.now());
        visit.setUser(user);
        visitRepository.save(visit);
    }
}
