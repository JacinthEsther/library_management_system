package com.techie.librarymanagementsystem.services.serviceInterfaces;

import com.techie.librarymanagementsystem.dtos.RegisterUserDto;
import com.techie.librarymanagementsystem.entities.User;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface UserService {

    String saveUser(RegisterUserDto request);

    User retrieveACustomerBy(String email);

    boolean isUserAdmin(String email, Authentication authentication);

    List<User> retrieveAllCustomers();

    Optional<User> findById(Long userId);
}
