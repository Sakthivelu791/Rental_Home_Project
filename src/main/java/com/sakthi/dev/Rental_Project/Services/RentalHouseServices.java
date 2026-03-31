package com.sakthi.dev.Rental_Project.Services;

import com.sakthi.dev.Rental_Project.Models.RentalHouse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import com.sakthi.dev.Rental_Project.Repository.RentalHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Getter
@Service
public class RentalHouseServices {
    @Autowired
    RentalHouseRepository rentalHouseRepository;

   public RentalHouse create(RentalHouse rentalHouse)
    {
        return rentalHouseRepository.save(rentalHouse);
    }
    public RentalHouse update(RentalHouse rentalHouse)
    {
        return rentalHouseRepository.save(rentalHouse);
    }
    public List<RentalHouse> GetAll()
    {
        return rentalHouseRepository.findAll();
    }
    public RentalHouse GetById(Long id)
    {
        return rentalHouseRepository.findById(id).orElseThrow(()->new RuntimeException("House Not Found"));
    }
    public List<RentalHouse> GetMyHouses(Long userId) {
        return rentalHouseRepository.findAllByuserId(userId);
    }

    public List<RentalHouse> GetAllHouse()
    {
        return rentalHouseRepository.findAll();
    }
    public ResponseEntity<String> DeleteHouse(RentalHouse rentalHouse){
        rentalHouseRepository.delete(GetById(rentalHouse.getId()));
        return new ResponseEntity<>("Delete successfully", HttpStatus.OK);
    }
   public RentalHouse findId(Long HouseId)
    {
        return rentalHouseRepository.findById(HouseId).orElseThrow(()->new RuntimeException("House Not Found"));
    }
}
