package com.api.placeup.services.impl;

import com.api.placeup.domain.entities.User;
import com.api.placeup.domain.repositories.UserRespository;
import com.api.placeup.exceptions.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRespository repository;

    @Transactional
    public User save(User user){
        return repository.save(user);
    }

    public UserDetails authenticate(User user) {
        UserDetails loadedUser = loadUserByUsername(user.getLogin());
        boolean matchesPassword = encoder.matches(user.getPassword(), loadedUser.getPassword());
        if(matchesPassword) {
            return loadedUser;
        }
        throw new InvalidPasswordException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String role = user.isSeller() ? "SELLER" : "CLIENT";

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(role)
                .build();
    }

}
