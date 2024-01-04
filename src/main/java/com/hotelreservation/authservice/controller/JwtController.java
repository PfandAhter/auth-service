package com.hotelreservation.authservice.controller;

import com.hotelreservation.authservice.authentication.JwtAuthenticationFilter;
import com.hotelreservation.authservice.authentication.JwtService;
import com.hotelreservation.authservice.model.entity.User;
import com.hotelreservation.authservice.repository.UserRepository;
import com.hotelreservation.authservice.request.AuthUserRequest;
import com.hotelreservation.authservice.request.BaseRequest;
import com.hotelreservation.authservice.response.AuthUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Filter;

@Controller
@Slf4j
@RequestMapping(path = "/jwt")
@CrossOrigin
@RequiredArgsConstructor

public class JwtController {

    private final JwtService jwtService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final UserRepository userRepository;


    //THIS IS REAL ONE
    /*@GetMapping(path = "/generateToken/{userid}")
    public ResponseEntity<AuthUserResponse> generateTokenForLogin(@PathVariable("userid") Long userid){
        User user = userRepository.findUserById(userid);
        AuthUserResponse authUserResponse = new AuthUserResponse();
        authUserResponse.setToken(jwtService.generateToken(user));

        return ResponseEntity.ok(authUserResponse);
    }*/
    @PostMapping(path = "/generateToken")
    public ResponseEntity<AuthUserResponse> generateTokenForLogin(@RequestBody AuthUserRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        AuthUserResponse authUserResponse = new AuthUserResponse();
        authUserResponse.setToken(jwtService.generateToken(user));

        return ResponseEntity.ok(authUserResponse);
    }

    /*@GetMapping(path = "/decryptToken/{token}")
    public ResponseEntity<String> extractUsernameFromToken(@PathVariable("token") String token) {
        String username = jwtService.extractUsername(jwtService.decryptJwt(token));
        return ResponseEntity.ok(username);
    }*/

    @PostMapping(path = "/extractUsername")
    public ResponseEntity<String> extractUsernameFromToken(@RequestBody BaseRequest token) {
        String username = jwtService.extractUsername(jwtService.decryptJwt(token.getToken().split(" ")[1]));
        return ResponseEntity.ok(username);
    }


    @GetMapping(path = "/encrpytToken/{token}")
    public ResponseEntity<String> encryptToken(@PathVariable("token") String token) {
        token = jwtService.decryptJwt(token.split(" ")[1]);
        return ResponseEntity.ok(token);
    }

    @PostMapping(path = "/checkTokenValid")
    public ResponseEntity<Boolean> checkToken(@RequestBody BaseRequest token){
        return ResponseEntity.ok(jwtService.isTokenValid(jwtService.decryptJwt(token.getToken().split(" ")[1]),
                userRepository.findByUsername(jwtService.extractUsername(jwtService.decryptJwt(token.getToken().split(" ")[1])))));
    }
}
