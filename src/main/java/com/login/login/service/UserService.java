package com.login.login.service;

import com.login.login.dto.UserLoginDTO;
import com.login.login.dto.UserLoginSucessDTO;
import com.login.login.dto.UserRegisterDTO;
import com.login.login.entities.User;
import com.login.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserRegisterDTO createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return new UserRegisterDTO(user.getId(), user.getEmail());
    }

    public Boolean findByUser(UserLoginDTO user) {
        User findByEmail = Optional.ofNullable(userRepository.findByEmail(user.getEmail())).orElseThrow(() -> new UsernameNotFoundException("Email or Password Not Found"));
        return encoder.matches(user.getPassword(), findByEmail.getPassword());
    }

    public User findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(() -> new UsernameNotFoundException("Email or Password Not Found"));
    }
}
