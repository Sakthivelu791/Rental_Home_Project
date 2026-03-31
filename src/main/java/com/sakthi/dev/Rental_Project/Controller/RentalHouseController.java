package com.sakthi.dev.Rental_Project.Controller;

import com.sakthi.dev.Rental_Project.Models.RentalHouse;
import com.sakthi.dev.Rental_Project.Repository.RentalHouseRepository;
import com.sakthi.dev.Rental_Project.Services.RentalHouseServices;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Getter
@Setter
@RestController

public class RentalHouseController {
    @Autowired
RentalHouseServices rentalHouseServices;
    private Long getCurrentUserId() {
        UsernamePasswordAuthenticationToken auth =
                (UsernamePasswordAuthenticationToken)
                        SecurityContextHolder.getContext().getAuthentication();
        return (Long) auth.getDetails();
    }

    @GetMapping
    ResponseEntity<List<RentalHouse>> GetAll()
    {
        return new ResponseEntity<>(rentalHouseServices.GetAll(),HttpStatus.OK);
    }

    @PostMapping("/create")
    ResponseEntity<RentalHouse> Create(@RequestBody RentalHouse rentalHouse)
 {
     System.out.println(getCurrentUserId());
    rentalHouse.setUserId(getCurrentUserId());
     return new ResponseEntity<>(rentalHouseServices.create(rentalHouse),HttpStatus.CREATED);
 }
    @PutMapping("/Update")
    ResponseEntity<?> Update(@RequestBody RentalHouse rentalHouse)
    {
        Long HouseId =rentalHouse.getId();
        RentalHouse HouseDetail=rentalHouseServices.findId(HouseId);

        if(getCurrentUserId() != HouseDetail.getUserId())
        {
            return new ResponseEntity<>("Invalid Access", HttpStatus.UNAUTHORIZED);
        }
        rentalHouse.setUserId(getCurrentUserId());
        return new ResponseEntity<>(rentalHouseServices.update(rentalHouse),HttpStatus.OK);
    }
    @GetMapping("/my-houses")
    ResponseEntity<List<RentalHouse>> getMyHouses() {
        Long userId = getCurrentUserId();
        return new ResponseEntity<>(rentalHouseServices.GetMyHouses(userId), HttpStatus.OK);
    }
    @GetMapping("/Home")
    ResponseEntity<List<RentalHouse>> getAllHouses()
    {
        return new ResponseEntity<>(rentalHouseServices.GetAllHouse(),HttpStatus.OK);
    }
    @DeleteMapping("/deleteMyHouse")
    ResponseEntity<String> Delete(@RequestBody RentalHouse rentalHouse)
    {
        Long HouseId =rentalHouse.getId();
        RentalHouse HouseDetail=rentalHouseServices.findId(HouseId);

        if(getCurrentUserId() != HouseDetail.getUserId())
        {
            return new ResponseEntity<>("Invalid Access", HttpStatus.UNAUTHORIZED);
        }
        return rentalHouseServices.DeleteHouse(rentalHouse);
    }


}
