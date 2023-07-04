package com.bagas.springrestfulapi.controller;

import com.bagas.springrestfulapi.model.LoginUserRequest;
import com.bagas.springrestfulapi.model.RegisterUserRequest;
import com.bagas.springrestfulapi.model.TokenResponse;
import com.bagas.springrestfulapi.model.WebResponse;
import com.bagas.springrestfulapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(
            path = "/api/auth/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<TokenResponse> login (LoginUserRequest request){
       TokenResponse response = authService.login(request);


        return WebResponse.<TokenResponse>builder().data(response).build();
    }
}
