package com.sakthi.dev.Rental_Project.Services;

import com.sakthi.dev.Rental_Project.Models.RentalHouse;
import com.sakthi.dev.Rental_Project.Models.User;
import com.sakthi.dev.Rental_Project.Repository.RentalHouseRepository;
import com.sakthi.dev.Rental_Project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepositoryRepository;

    public User createUser(User user)
    {
        return userRepositoryRepository.save(user);
    }
    public User GetById(Long id)
    {
        return userRepositoryRepository.findById(id).orElseThrow(()->new RuntimeException());
    }
}
