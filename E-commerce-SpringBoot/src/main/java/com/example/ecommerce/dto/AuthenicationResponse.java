package com.example.ecommerce.dto;

import lombok.Data;

@Data
public class AuthenicationResponse {
    
    private String jwtToken;

    public AuthenicationResponse(String jwt) {
    }
}
