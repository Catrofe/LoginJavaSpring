package com.login.login.service;

import com.login.login.entities.User;
import com.login.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User login(User user) {
        return userRepository.save(user);
    }
}
