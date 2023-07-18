package com.bagas.springrestfulapi.controller;

import com.bagas.springrestfulapi.entity.Address;
import com.bagas.springrestfulapi.entity.User;
import com.bagas.springrestfulapi.model.RegisterUserRequest;
import com.bagas.springrestfulapi.model.UpdateUserRequest;
import com.bagas.springrestfulapi.model.UserResponse;
import com.bagas.springrestfulapi.model.WebResponse;
import com.bagas.springrestfulapi.repository.AddressRepository;
import com.bagas.springrestfulapi.repository.ContactRepository;
import com.bagas.springrestfulapi.repository.UserRepository;
import com.bagas.springrestfulapi.security.BCrypt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        addressRepository.deleteAll();
        contactRepository.deleteAll();
        userRepository.deleteAll();

    }

    @Test
    void resgisterSuccess ()throws Exception{

        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("test");
        request.setPassword("test");
        request.setName("test");

        mockMvc.perform(
                post("/api/users")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String>response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals("OK", response.getData());
        });
    }

    @Test
    void resgisterBadRequest ()throws Exception{

        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("test");
        request.setPassword("");
        request.setName("");

        mockMvc.perform(
                post("/api/users")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String>response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getErrors());

        });
    }

    @Test
    void resgisterDuplicate ()throws Exception{

        User user = new User();
        user.setName("test");
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("test",BCrypt.gensalt()));
        userRepository.save(user);

        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("test");
        request.setPassword("test");
        request.setName("test");

        mockMvc.perform(
                post("/api/users")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String>response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getErrors());

        });
    }

    @Test
    void getUserUnauthorized ()throws Exception{


        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("X-API-TOKEN","wrongtoken")
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String>response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getErrors());

        });
    }

    @Test
    void getUserUnauthorizedWithoutToken ()throws Exception{


        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<String>response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getErrors());

        });
    }

    @Test
    void getUserSuccess ()throws Exception{

        User user = new User();
        user.setName("test");
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("test",BCrypt.gensalt()));
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis()+1_000_000);
        userRepository.save(user);

        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("X-API-TOKEN","test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<UserResponse>response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals("test", response.getData().getName());
            assertEquals("test",response.getData().getUsername());

        });
    }

    @Test
    void getUserTokenExpired ()throws Exception{

        User user = new User();
        user.setName("test");
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("test",BCrypt.gensalt()));
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis()-1_000_000);
        userRepository.save(user);

        mockMvc.perform(
                get("/api/users/current")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("X-API-TOKEN","test")
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<UserResponse>response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getErrors());

        });
    }

    @Test
    void updateUserUnauthorized()throws Exception{
        UpdateUserRequest request = new UpdateUserRequest();

        mockMvc.perform(
                patch("/api/users/current")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        ).andExpectAll(
                status().isUnauthorized()
        ).andDo(result -> {
            WebResponse<UserResponse>response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response.getErrors());


        });
    }

    @Test
    void updateUserSuccess ()throws Exception{

        User user = new User();
        user.setName("test");
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("test",BCrypt.gensalt()));
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis()+1_000_000);
        userRepository.save(user);

        UpdateUserRequest request = new UpdateUserRequest();
        request.setPassword("password");
        request.setName("bagas");

        mockMvc.perform(
                patch("/api/users/current")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("X-API-TOKEN","test")
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<UserResponse>response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNull(response.getErrors());
            assertEquals("bagas",response.getData().getName());
            assertEquals("test",response.getData().getUsername());

            User dbUser = userRepository.findById("test").orElse(null);

            assertNotNull(dbUser);
            assertTrue(BCrypt.checkpw("password", dbUser.getPassword()));

        });
    }
}
