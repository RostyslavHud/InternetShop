package com.internetshop.mysqlRepository;

import com.internetshop.mysqlModel.UserAttempts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAttemptsRepository extends JpaRepository<UserAttempts, Long> {

    Optional<UserAttempts> findByUsername(String username);
}
