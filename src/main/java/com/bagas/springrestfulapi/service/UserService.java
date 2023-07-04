package com.bagas.springrestfulapi.service;

import com.bagas.springrestfulapi.entity.User;
import com.bagas.springrestfulapi.model.RegisterUserRequest;
import com.bagas.springrestfulapi.repository.UserRepository;
import com.bagas.springrestfulapi.security.BCrypt;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void register (RegisterUserRequest request){
        validationService.validate(request);

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword(),BCrypt.gensalt()));
        user.setName(request.getName());

        userRepository.save(user);


    }
}
