package com.management.fullstack.controller;

import com.management.fullstack.exception.UserNotFoundException;
import com.management.fullstack.model.User;
import com.management.fullstack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/addUser")
    public User addUsers(@RequestBody User user){
        return userRepository.save(user);
    }
    @GetMapping("/users")
    public List<User> getUsers(){
        return userRepository.findAll();
    }
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException(id));
    }

    @PutMapping("/update/{id}")
    public User updateUser(@RequestBody User newUser, @PathVariable Long id){
        return  userRepository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setUsername(newUser.getUsername());
                    user.setEmail(newUser.getEmail());
                    return userRepository.save(user);
                }).orElseThrow(()-> new UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    public String deleteUserById(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return "User with id "+id+" deleted successfully";
    }
}
