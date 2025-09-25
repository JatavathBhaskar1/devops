package com.techie.service;

import com.techie.document.User;
import com.techie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataInitializationService implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        createDefaultAdminUser();
    }

    private void createDefaultAdminUser() {
        //check if admin already exits
        if (!userRepository.existsByEmail("admin@musify.com")) {
            User adminuser = User.builder()
                    .email("admin@musify.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(User.Role.ADMIN)
                    .build();
            userRepository.save(adminuser);
            log.info("Admin user created: email is admin@musify.com and password is admin123");
        }else {
            log.info("Admin user already exists");
        }
    }
}
