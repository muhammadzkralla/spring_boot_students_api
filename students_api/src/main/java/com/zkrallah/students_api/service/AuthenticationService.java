package com.zkrallah.students_api.service;

import com.zkrallah.students_api.dtos.LoginUserDto;
import com.zkrallah.students_api.dtos.RegisterUserDto;
import com.zkrallah.students_api.entity.User;
import com.zkrallah.students_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User signup(RegisterUserDto input) {
        User user = new User();
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getEmail(),
                        loginUserDto.getPassword()
                )
        );

        return userRepository.findByEmail(loginUserDto.getEmail())
                .orElseThrow();
    }
}
