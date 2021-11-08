package com.jasdom.user_module.user_module.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jasdom.user_module.user_module.model.User;
import com.jasdom.user_module.user_module.service.ServiceResponse;
import com.jasdom.user_module.user_module.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@WebMvcTest(value = UserRestController.class)
public class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService userService;

    @AfterEach
    void tearDown() {
        reset(userService);
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("userRestController"));
    }

    @Test
    public void testUsersJson() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius"));
        users.add(new User("Petras", "Petratis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius"));
        when(userService.findAll()).thenReturn(users);

        RequestBuilder rb = MockMvcRequestBuilders
                .get("/users")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String expected = new ObjectMapper().writeValueAsString(users);
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testUserById() throws Exception {
        User user = new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius");
        when(userService.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        RequestBuilder rb = MockMvcRequestBuilders
                .get("/users/1")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String expected = new ObjectMapper().writeValueAsString(user);
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testAddUser() throws Exception{
        User user = new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius");
        String json = new ObjectMapper().writeValueAsString(user);

        when(userService.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        when(userService.add(Mockito.any(User.class))).thenReturn(new ServiceResponse<>(user));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("http://localhost/users/0", response.getHeader(HttpHeaders.LOCATION));
    }

    @Test
    public void testUpdateUser() throws Exception{
        User user = new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius");
        String json = new ObjectMapper().writeValueAsString(user);

        when(userService.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        when(userService.update(Mockito.any(User.class))).thenReturn(new ServiceResponse<>(user));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/users/0")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("http://localhost/users/0", response.getHeader(HttpHeaders.LOCATION));
    }
}
