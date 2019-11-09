package com.testproject.controller;

import com.testproject.domain.Role;
import com.testproject.domain.User;
import com.testproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;


@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String registration(){
        return "registration";
    }

    @PostMapping
    public String addUser(User user, Map<String, Object> model){
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if(userFromDb != null){
            model.put("message", "User exists!");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }
}
