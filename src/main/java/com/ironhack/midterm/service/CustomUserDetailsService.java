package com.ironhack.midterm.service;


import com.ironhack.midterm.model.User;
import com.ironhack.midterm.repository.UserRepository;
import com.ironhack.midterm.security.CustomUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if(!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(optionalUser.get());

        return customUserDetails;
    }

}