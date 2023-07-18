package com.bagas.springrestfulapi.service;

import com.bagas.springrestfulapi.entity.User;
import com.bagas.springrestfulapi.model.RegisterUserRequest;
import com.bagas.springrestfulapi.model.UpdateUserRequest;
import com.bagas.springrestfulapi.model.UserResponse;
import com.bagas.springrestfulapi.repository.UserRepository;
import com.bagas.springrestfulapi.security.BCrypt;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void register (RegisterUserRequest request){
        validationService.validate(request);

        if (userRepository.existsById(request.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username already registered");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(),BCrypt.gensalt()));
        user.setName(request.getName());

        userRepository.save(user);


    }

    public UserResponse get(User user){
        return UserResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .build();

    }

    @Transactional
    public UserResponse update(User user, UpdateUserRequest request){
        validationService.validate(request);

        if (Objects.nonNull(request.getPassword())){
            user.setPassword(BCrypt.hashpw(request.getPassword(),BCrypt.gensalt()));
        }

        if (Objects.nonNull(request.getName())){
            user.setName(request.getName());
        }

        userRepository.save(user);

        return UserResponse.builder()
                .username(user.getUsername())
                .name(user.getName())
                .build();
    }

}
