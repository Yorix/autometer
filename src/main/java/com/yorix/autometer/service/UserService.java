package com.yorix.autometer.service;

import com.yorix.autometer.model.Role;
import com.yorix.autometer.model.User;
import com.yorix.autometer.storage.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService extends AppService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username).orElseThrow();
    }

    public User getUser(String username) {
        return userRepository.findById(username).orElse(null);
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
}
