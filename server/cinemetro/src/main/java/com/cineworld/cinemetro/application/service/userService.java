package com.cineworld.cinemetro.application.service;

import com.cineworld.cinemetro.domain.enums.UserRole;

import com.cineworld.cinemetro.domain.model.User;
import com.cineworld.cinemetro.persistence.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userService {

    private final userRepository userRepository;
    @Autowired
    public userService (userRepository userRepository){
        this.userRepository = userRepository;
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public List<User> getByRole(UserRole userRole){
        return userRepository.findByRole(userRole);
    }

    public User createUser(User user){
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already exists!"); //Later to put in Exception file
        }
        return userRepository.save(user);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUserById(Long id){
        if(!userRepository.existsById(id)){
            //To be done
        }
        userRepository.deleteById(id);
    }

}