package com.login.login.service;

import com.login.login.dto.ChangePassword;
import com.login.login.dto.UserGetDTO;
import com.login.login.dto.UserLoginDTO;
import com.login.login.dto.UserRegisterDTO;
import com.login.login.entities.User;
import com.login.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    public UserGetDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return new UserGetDTO(user.getId(), user.getEmail(), user.getName(), user.getCpf(), user.getPhone());
    }

    public UserLoginDTO changePassword(Long id, ChangePassword password) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        user.setPassword(encoder.encode(password.getPassword()));
        user = userRepository.save(user);
        return new UserLoginDTO(user.getId().toString(), user.getEmail());
    }
}
