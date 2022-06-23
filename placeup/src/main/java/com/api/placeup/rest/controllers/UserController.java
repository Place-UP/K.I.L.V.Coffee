package com.api.placeup.rest.controllers;

import com.api.placeup.domain.entities.User;
import com.api.placeup.exceptions.InvalidPasswordException;
import com.api.placeup.rest.dto.CredentialsDTO;
import com.api.placeup.rest.dto.TokenDTO;
import com.api.placeup.security.jwt.JwtService;
import com.api.placeup.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody @Valid User user ){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userService.save(user);
    }

    @PostMapping("/auth")
    public TokenDTO authentication(@RequestBody CredentialsDTO credentials) {
        try {
            User user = User.builder()
                    .login( credentials.getLogin() )
                    .password( credentials.getPassword() )
                    .build();

            UserDetails authenticatedUser = userService.authenticate(user);
            String token = jwtService.generateToken(user);

            return new TokenDTO(user.getLogin(), token);

        } catch (UsernameNotFoundException | InvalidPasswordException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}