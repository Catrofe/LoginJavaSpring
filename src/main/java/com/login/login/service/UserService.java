package com.login.login.service;

import com.login.login.dto.UserRegisterDTO;
import com.login.login.entities.User;
import com.login.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public UserRegisterDTO createUser(User user) {
        user = userRepository.save(user);
        return new UserRegisterDTO(user.getId(), user.getEmail());
    }

    public User login(User user) {
        return userRepository.save(user);
    }
}
