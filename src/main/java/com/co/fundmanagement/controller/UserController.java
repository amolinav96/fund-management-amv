package com.co.fundmanagement.controller;

import com.co.fundmanagement.model.User;
import com.co.fundmanagement.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/byId")
    public Mono<User> findById(@RequestParam String id){
        return userService.findById(id);
    }
}
