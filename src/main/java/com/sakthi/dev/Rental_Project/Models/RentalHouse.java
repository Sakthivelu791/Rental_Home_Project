package com.sakthi.dev.Rental_Project.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
public class RentalHouse {
    @Id
    @GeneratedValue
    Long id;
    String Location;
    Long PhoneNumber;
    String MapLink;
    Long RentPrice;
    Long userId;
}
