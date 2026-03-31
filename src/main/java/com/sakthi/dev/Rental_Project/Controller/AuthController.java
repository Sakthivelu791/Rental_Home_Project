package com.sakthi.dev.Rental_Project.Controller;

import com.sakthi.dev.Rental_Project.Models.User;
import com.sakthi.dev.Rental_Project.Repository.UserRepository;
import com.sakthi.dev.Rental_Project.Services.UserServices;
import com.sakthi.dev.Rental_Project.utils.JwtUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
@Getter
@Builder
public class AuthController {
    private final UserServices userServices;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @PostMapping("/register")
    public ResponseEntity<String> RegisterUser(@RequestBody Map<String,String> body)
    {
        String email=body.get("email");
        String password=passwordEncoder.encode(body.get("password"));

        if(userRepository.findByEmail(email).isPresent())
        {
            return new ResponseEntity<>("Email All ready Exist", HttpStatus.CONFLICT);
        }
        userServices.createUser(User.builder().email(email).password(password).build());
        return new ResponseEntity<>("Successfully Registerd", HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        var userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email");
        }

        var user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        String token = jwtUtil.generateToken(email,user.getId());
        return ResponseEntity.ok(Map.of("token", token));
    }
}

