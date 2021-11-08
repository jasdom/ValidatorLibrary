package com.jasdom.user_module.user_module.controller;

import com.jasdom.user_module.user_module.model.User;
import com.jasdom.user_module.user_module.service.ServiceResponse;
import com.jasdom.user_module.user_module.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(value = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserService userService;

    @Test
    public void givenWac_whenServletContext_thenItProvidesController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("userController"));
    }

    @Test
    public void testShowAll() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122","Vilnius"));
        users.add(new User("Petras", "Petratis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122","Vilnius"));
        when(userService.findAll()).thenReturn(users);

        RequestBuilder rb = MockMvcRequestBuilders.get("/list-users").accept(MediaType.TEXT_HTML);

        mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk()) // 200
                .andExpect(MockMvcResultMatchers.view().name("list-users"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/list-users.jsp"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andReturn();
    }

    @Test
    public void testShowAddPage() throws Exception {
        RequestBuilder rb = MockMvcRequestBuilders.get("/add-user");

        mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/user.jsp"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", hasProperty("name", emptyOrNullString())))
                .andExpect(MockMvcResultMatchers.model().attribute("user", hasProperty("surname", emptyOrNullString())))
                .andExpect(MockMvcResultMatchers.model().attribute("user", hasProperty("emailAddress", emptyOrNullString())))
                .andExpect(MockMvcResultMatchers.model().attribute("user", hasProperty("phoneNumber", emptyOrNullString())))
                .andExpect(MockMvcResultMatchers.model().attribute("user", hasProperty("password", emptyOrNullString())))
                .andExpect(MockMvcResultMatchers.model().attribute("user", hasProperty("address", emptyOrNullString())))
                .andReturn();
    }

    @Test
    public void testAdd() throws Exception {

        User user = new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122","Vilnius");
        when(userService.add(Mockito.any(User.class))).thenReturn(new ServiceResponse<>(user));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/add-user")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "Jonas")
                .param("lastName", "Jonaitis")
                .param("emailAddress", "jonas@gmail.com")
                .param("phoneNumber", "+37014758315")
                .param("password", "TestPasswor-d122")
                .param("address", "Vilnius")
                .flashAttr("user", new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122","Vilnius"));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/list-users"))
                .andReturn();

        verify(userService).add(Mockito.any(User.class));
    }

    @Test
    public void testShowUpdatePage() throws Exception {
        User user = new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius");
        when(userService.findById(Mockito.anyLong())).thenReturn(Optional.of((user)));

        RequestBuilder rb = MockMvcRequestBuilders.get("/update-user/1");

        mockMvc.perform(rb)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/user.jsp"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", hasProperty("name", Matchers.equalTo("Jonas"))))
                .andExpect(MockMvcResultMatchers.model().attribute("user", hasProperty("surname", Matchers.equalTo("Jonaitis"))))
                .andExpect(MockMvcResultMatchers.model().attribute("user", hasProperty("emailAddress", Matchers.equalTo("jonas@gmail.com"))))
                .andExpect(MockMvcResultMatchers.model().attribute("user", hasProperty("phoneNumber", Matchers.equalTo("+37014758315"))))
                .andExpect(MockMvcResultMatchers.model().attribute("user", hasProperty("password", Matchers.equalTo("TestPasswor-d122"))))
                .andExpect(MockMvcResultMatchers.model().attribute("user", hasProperty("address", Matchers.equalTo("Vilnius"))))
                .andReturn();
    }

    @Test
    public void testUpdate() throws Exception {
        User user = new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122","Vilnius");
        when(userService.update(Mockito.any(User.class))).thenReturn(new ServiceResponse<>(user));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/update-user/1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "Jonas")
                .param("lastName", "Jonaitis")
                .param("emailAddress", "jonas@gmail.com")
                .param("phoneNumber", "+37014758315")
                .param("password", "TestPasswor-d122")
                .param("address", "Vilnius")
                .flashAttr("user", new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122","Vilnius"));

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/list-users"))
                .andReturn();

        verify(userService).update(Mockito.any(User.class));
    }

    @Test
    public void testDelete() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/delete-user/1");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/list-users"))
                .andReturn();

        verify(userService).deleteById(Mockito.anyLong());
    }

}
