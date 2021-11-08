package com.jasdom.user_module.user_module.service;

import com.jasdom.user_module.user_module.model.User;
import com.jasdom.user_module.user_module.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository repository;

    @Mock
    UserValidator validatorService;

    @InjectMocks
    UserService service;

    @Test
    public void testFindAll(){
        User user = new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius");

        List<User> users = new ArrayList<>();
        users.add(user);

        when(repository.findAll()).thenReturn(users);

        List<User> found = service.findAll();

        verify(repository).findAll();

        assertEquals(1, found.size());
    }

    @Test
    public void testFindById(){
        User user = new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius");

        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        Optional<User> found = service.findById(1);
        assertTrue(found.isPresent());

        verify(repository).findById(Mockito.anyLong());
    }

    @Test
    void testAdd() {
        User user = new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius");
        when(repository.save(Mockito.any(User.class))).thenReturn(user);
        when(validatorService.validateUser(Mockito.any(User.class))).thenReturn(new ServiceResponse<>(null));

        ServiceResponse<User> response = service.add(user);
        assertTrue(response.success);
        User added = response.data;
        verify(repository).save(Mockito.any(User.class));
        verify(validatorService).validateUser(Mockito.any(User.class));
        assertNotNull(added);
    }

    @Test
    void testUpdate() {
        User user = new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius");

        when(validatorService.validateUser(Mockito.any(User.class))).thenReturn(new ServiceResponse<>(null));
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        ServiceResponse<User> response = service.update(user);

        assertTrue(response.success);
        verify(repository).save(Mockito.any(User.class));
        verify(repository).findById(Mockito.anyLong());
        verify(validatorService).validateUser(Mockito.any(User.class));
    }

    @Test
    void testDeleteById() {
        User user = new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius");
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        ServiceResponse<User> response = service.deleteById(1);
        assertTrue(response.success);
        verify(repository).deleteById(Mockito.anyLong());
    }

    @Test
    void testDelete() {
        User user = new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius");
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

        ServiceResponse<User> response = service.delete(user);
        assertTrue(response.success);
        verify(repository).deleteById(Mockito.anyLong());
    }

}
