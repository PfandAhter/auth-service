package com.hotelreservation.authservice.repository;

import com.hotelreservation.authservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findUserById(Long userid);

    User findByUsername(String username);
}
