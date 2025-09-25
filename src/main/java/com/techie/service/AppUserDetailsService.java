package com.techie.service;

import com.techie.document.User;
import com.techie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User existingUser= userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User Not Found with email"));
        return new org.springframework.security.core.userdetails.User(existingUser.getEmail(),
                existingUser.getPassword(),existingUser.getAuthorities(existingUser));
    }

}
