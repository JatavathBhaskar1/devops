package com.techie.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
