package com.co.fundmanagement.service.impl;

import com.co.fundmanagement.exception.UserNotFoundException;
import com.co.fundmanagement.model.User;
import com.co.fundmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.co.fundmanagement.enums.ErrorEnum.USER_NOT_FOUND;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Mono<User> findById(String id){
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException(USER_NOT_FOUND.getMessage())));
    }
}
