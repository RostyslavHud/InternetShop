package com.internetshop.mysqlRepository;

import com.internetshop.mysqlModel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select count (u.name) from User u where u.name = ?1")
    int countName(String userName);

    @Query("select count(u.email) from User u where u.email = ?1")
    int countEmail(String email);

    User findByName(String name);

    User findByEmail(String email);
}
