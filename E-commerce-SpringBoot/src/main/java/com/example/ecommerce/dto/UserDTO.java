package com.example.ecommerce.dto;

import com.example.ecommerce.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private long id;
    private String name;
    private String email;
    private String password;
    private UserRole userRole;
    private byte[] img;


    public UserDTO(long id, String email, String name, UserRole userRole) {
    }
}
