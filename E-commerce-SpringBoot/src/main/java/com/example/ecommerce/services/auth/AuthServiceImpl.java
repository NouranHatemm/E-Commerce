package com.example.ecommerce.services.auth;

import com.example.ecommerce.dto.SignupDto;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.entities.Order;
import com.example.ecommerce.entities.User;
import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.enums.UserRole;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;

    private PasswordEncoder bCryptPasswordEncoder;
    private final OrderRepository orderRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.orderRepository = orderRepository;
    }

    public UserDTO createUser(SignupDto signupDto) {
        User user = new User();

        user.setEmail(signupDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(signupDto.getPassword()));
        user.setName(signupDto.getName());
        user.setUserRole(UserRole.CUSTOMER);
        User createdUser = userRepository.save(user);

        Order order = new Order();
        order.setAmount(0L);
        order.setUser(createdUser);
        order.setOrderStatus(OrderStatus.Pending);
        order.setTotalAmount(0L);
        orderRepository.save(order);

        UserDTO userDTO = new UserDTO(createdUser.getId(), createdUser.getEmail(), createdUser.getName(), createdUser.getUserRole());
        userDTO.setId(createdUser.getId());

        return userDTO;

    }

    public Boolean hasUserWithEmail(String email) {
        User user = userRepository.findFirstByEmail(email);
        return user != null;
    }

    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if (adminAccount == null) {
            User user = new User();
            user.setEmail("admin@gmail.com");
//            user.setPassword(new BCryptPasswordEncoder.encode("admin"));
            user.setName("admin");
            user.setUserRole(UserRole.ADMIN);
            user.setPassword(bCryptPasswordEncoder.encode("admin"));
            userRepository.save(user);

        }
    }

}
