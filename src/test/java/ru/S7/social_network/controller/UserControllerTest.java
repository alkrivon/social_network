package ru.S7.social_network.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.S7.social_network.dto.UserDtoResponse;

import ru.S7.social_network.repository.UserRepository;
import ru.S7.social_network.security.jwt.JwtTokenFilter;
import ru.S7.social_network.security.jwt.JwtTokenProvider;
import ru.S7.social_network.service.UserService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @MockBean
    UserController userController;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    JwtTokenFilter jwtTokenFilter;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @Test
    void getUserTest() throws Exception {

        when(userController.getUser(anyLong())).thenReturn(
                new UserDtoResponse(1L, "Test", "Test", 1L));

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/user/get/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Test"))
                .andExpect(status().isOk());
    }

    @Test
    void getUserByUsernameTest() throws Exception {

        when(userController.getUserByName(anyString())).thenReturn(
                new UserDtoResponse(1L, "Test", "Test", 1L));

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/user/get?username=Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Test"))
                .andExpect(status().isOk());
    }
}