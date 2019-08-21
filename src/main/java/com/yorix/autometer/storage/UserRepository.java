package com.yorix.autometer.storage;

import com.yorix.autometer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
