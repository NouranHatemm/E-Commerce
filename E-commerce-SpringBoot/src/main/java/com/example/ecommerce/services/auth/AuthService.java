package com.example.ecommerce.services.auth;

import com.example.ecommerce.dto.SignupDto;
import com.example.ecommerce.dto.UserDTO;

public interface AuthService {
    UserDTO createUser(SignupDto userDTO);

    Boolean hasUserWithEmail(String email);

    void createAdminAccount();
}