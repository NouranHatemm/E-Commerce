package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AuthenticationRequest;
import com.example.ecommerce.dto.SignupDto;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.entities.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.services.auth.AuthService;
import com.example.ecommerce.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", allowedHeaders = "", exposedHeaders = "Cache-Contro l, Content-Language, Content-Type, Expires, Last-Modified")
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

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                                       HttpServletResponse response) throws Exception {


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


    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupDto signupDto) throws Exception {


        if (authService.hasUserWithEmail(signupDto.getEmail())) {
            return new ResponseEntity<>("User already exist", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDTO userDTO = authService.createUser(signupDto);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);

    }
}