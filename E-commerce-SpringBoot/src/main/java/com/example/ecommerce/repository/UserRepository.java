package com.example.ecommerce.repository;

import com.example.ecommerce.entities.User;
import com.example.ecommerce.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    User findFirstByEmail(String email);

    User findFirstByEmail(String email);

    User findFirstByName(String user);

    User findByUserRole(UserRole userRole);
}
