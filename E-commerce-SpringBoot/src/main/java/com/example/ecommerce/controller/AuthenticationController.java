package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AuthenticationRequest;
import com.example.ecommerce.dto.SignupDto;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.entities.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.services.auth.AuthService;
import com.example.ecommerce.services.user.UserService;
import com.example.ecommerce.utils.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
//@RequiredArgsConstructor
public class AuthenticationController {

//    @Autowired
//    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;


    // check at DB
    // if found return true else false
//        if true call anything as per business need
    @CrossOrigin(origins = "*")
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                          HttpServletResponse response, HttpSecurity http)

            throws Exception {

        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);



        User user = userRepository.findFirstByEmail(authenticationRequest.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        final String jwt = jwtUtil.generateToken(user);

        JSONObject jsonResponse = new JSONObject()
                .put("userID", user.getId())
                .put("role", user.getUserRole())
                .put("token", "Bearer " + jwt);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);

        return ResponseEntity.ok().body(jsonResponse.toString());

    }

    @CrossOrigin(origins = "*")
    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupDto signupDto, HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);

        if (authService.hasUserWithEmail(signupDto.getEmail())) {
            return new ResponseEntity<>("User already exist", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDTO userDTO = authService.createUser(signupDto);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);

    }
}

