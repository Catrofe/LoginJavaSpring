package com.login.login.controller;

import com.login.login.dto.UserLoginDTO;
import com.login.login.dto.UserLoginSucessDTO;
import com.login.login.dto.UserRegisterDTO;
import com.login.login.entities.User;
import com.login.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.login.login.security.config.JwtUtil;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/users")
public class UserControllers {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserRegisterDTO> createUser(@RequestBody User user) {
        try{
            UserRegisterDTO userCreated = userService.createUser(user);
            return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserLoginSucessDTO> login(@RequestBody UserLoginDTO user) {
        Boolean byUser = userService.findByUser(user);
        User byEmail = userService.findByEmail(user.getEmail());
        String token = jwtUtil.generationToken(byEmail.getId());
        if (byUser) {
            return new ResponseEntity<>(new UserLoginSucessDTO(1L, user.getEmail(), token), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


}
