package com.login.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.login.login.dto.*;
import com.login.login.entities.User;
import com.login.login.security.util.Extration;
import com.login.login.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.login.login.security.config.JwtUtil;

import java.util.Optional;

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
        try{
            Boolean byUser = userService.findByUser(user);
            if (byUser) {
                User byEmail = userService.findByEmail(user.getEmail());
                String token = jwtUtil.generationToken(byEmail.getId());
                return new ResponseEntity<>(new UserLoginSucessDTO(1L, user.getEmail(), token), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping
    public ResponseEntity<UserGetDTO> findById(@RequestHeader(value = "Authorization") String token) throws JsonProcessingException {
        Claims claims = jwtUtil.getClaims(token.replace("Bearer ", ""));
        Extration extration = new Extration();
        Long id = extration.extrationId(claims);
        UserGetDTO byId = userService.findById(id);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @PatchMapping(value = "/password")
    public ResponseEntity<UserLoginDTO> changePassword(@RequestBody ChangePassword password, @RequestHeader(value = "Authorization") String token) throws JsonProcessingException {
        Claims claims = jwtUtil.getClaims(token.replace("Bearer ", ""));
        Extration extration = new Extration();
        Long id = extration.extrationId(claims);
        UserLoginDTO byId = userService.changePassword(id, password);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @PatchMapping(value = "")
    public ResponseEntity<UserGetDTO> editUser(@RequestBody UserEdit user, @RequestHeader(value = "Authorization") String token) throws JsonProcessingException {
        Claims claims = jwtUtil.getClaims(token.replace("Bearer ", ""));
        Extration extration = new Extration();
        Long id = extration.extrationId(claims);
        try {
            User byEmail = userService.findByEmail(user.getEmail());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }catch (Exception e){
            UserGetDTO byId = userService.update(id, user);
            return new ResponseEntity<>(byId, HttpStatus.OK);
        }
    }
}
