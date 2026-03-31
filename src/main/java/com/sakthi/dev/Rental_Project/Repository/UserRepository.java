package com.sakthi.dev.Rental_Project.Repository;

import com.sakthi.dev.Rental_Project.Models.RentalHouse;
import com.sakthi.dev.Rental_Project.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
     Optional<User> findByEmail(String email);
}
