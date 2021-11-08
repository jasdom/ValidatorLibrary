package com.jasdom.user_module.user_module.repository;

import com.jasdom.user_module.user_module.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @Test
    public void testSave(){
        User user = new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius");
        User saved = repository.save(user);

        assertNotNull(saved);
        assertTrue(saved.getId() != 0);
        assertEquals("Jonas", saved.getName());
        assertEquals("Jonaitis", saved.getSurname());
        assertEquals("jonas@gmail.com", saved.getEmailAddress());
        assertEquals("+37014758315", saved.getPhoneNumber());
        assertEquals("TestPasswor-d122", saved.getPassword());
        assertEquals("Vilnius", saved.getAddress());
    }

    @Test
    public void testFindAll(){
        repository.save(new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius"));
        repository.save(new User("Petras", "Petratis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius"));
        repository.save(new User("Tomas", "Tomukas", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius"));

        List<User> users = (List<User>) repository.findAll();
        assertNotNull(users);
        assertEquals(3, users.size());
    }

    @Test
    public void testFindById(){
        User user = repository.save(new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius"));

        Optional<User> found = repository.findById(user.getId());
        assertTrue(found.isPresent());

        assertEquals("+37014758315", found.get().getPhoneNumber());
    }

    @Test
    public void testDeleteById(){
        User user = repository.save(new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius"));

        Optional<User> found = repository.findById(user.getId());
        assertTrue(found.isPresent());

        repository.deleteById(user.getId());

        found = repository.findById(user.getId());
        assertTrue(found.isEmpty());
    }

    @Test
    public void testDelete(){
        User user = repository.save(new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius"));

        Optional<User> found = repository.findById(user.getId());
        assertTrue(found.isPresent());

        repository.delete(user);

        found = repository.findById(user.getId());
        assertTrue(found.isEmpty());
    }

}
