package com.example.ecommerce.services.jwt;

import com.example.ecommerce.entities.User;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User optionalUser = userRepository.findFirstByEmail(username);
      if (optionalUser == null) {
          throw new UsernameNotFoundException("User not found", null);
      }

      return new org.springframework.security.core.userdetails.User(optionalUser.getEmail(), optionalUser.getPassword(), new ArrayList<>());

//        User optionalUser = userRepository.findFirstByEmail(username);
//        if (optionalUser == null) throw new UsernameNotFoundException("Username not found", null);
//        return new org.springframework.security.core.userdetails.User(optionalUser.getEmail(), optionalUser.getPassword(), new ArrayList<>());

    }
}
