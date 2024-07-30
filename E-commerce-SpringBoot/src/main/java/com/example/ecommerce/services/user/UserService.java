package com.example.ecommerce.services.user;

import com.example.ecommerce.dto.SignupDto;
import com.example.ecommerce.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public UserDTO createUser(SignupDto signupDto);

    public boolean hasUserWithEmail(String email);

}
