package com.pbo.urlshortener.services;

import com.pbo.urlshortener.models.Role;
import com.pbo.urlshortener.models.User;
import com.pbo.urlshortener.repositories.RoleRepository;
import com.pbo.urlshortener.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setStatus("VERIFIED");
        Role userRole = roleRepository.findByRole("SITE_USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public boolean isUserAlreadyPresent(User user) {
        User existedUser = userRepository.findByEmail(user.getEmail());
        if (existedUser != null) {
            return true;
        }
        return false;
    }
}
