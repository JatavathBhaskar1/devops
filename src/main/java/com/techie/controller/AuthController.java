package com.techie.controller;

import com.techie.document.User;
import com.techie.dto.AuthRequest;
import com.techie.dto.AuthResponse;
import com.techie.dto.RegisterRequest;
import com.techie.dto.UserResponse;
import com.techie.service.AppUserDetailsService;
import com.techie.service.UserService;
import com.techie.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {


            log.info("request body:" +request);
            //authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            log.info("User authenticated successfully");

            //load the user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            User existingUser = userService.findByEmail(request.getEmail());
            log.info("User details loaded successfully");
            //generate jwt token
            String token = jwtUtil.generateToken(userDetails, existingUser.getRole().name());

            return ResponseEntity.ok(new AuthResponse(token, request.getEmail(), existingUser.getRole().name()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        System.out.println("Received Register Payload: " + request);

        String email = request.getEmail();
        String password = request.getPassword();
        System.out.println("Email: " + email + ", Password: " + password);
        try {
            UserResponse response = userService.registerUser(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

}
