package com.sakthi.dev.Rental_Project.Repository;

import com.sakthi.dev.Rental_Project.Models.RentalHouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalHouseRepository extends JpaRepository<RentalHouse,Long> {

    List<RentalHouse> findAllByuserId(Long userId);
}
