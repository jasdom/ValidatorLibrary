package com.jasdom.user_module.user_module.service;

import com.jasdom.user_module.user_module.model.User;
import com.jasdom.user_module.user_module.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    private UserValidator validator;

    protected UserService() {
        validator = new UserValidator();
    }

    public List<User> findAll() {
        return (List<User>)repository.findAll();
    }

    public Optional<User> findById(long id){
        return repository.findById(id);
    }

    public ServiceResponse<User> add(User user){

        ServiceResponse<User> response = validator.validateUser(user);

        if(!response.success) return new ServiceResponse<>(false, null, response.message);

        User saved = repository.save(user);
        return new ServiceResponse<>(saved);
    }

    public ServiceResponse<User> update(User user){
        Optional<User> found = findById(user.getId());

        if(found.isEmpty()) return new ServiceResponse<>(false, null, "User with id: " + user.getId() + " not found.");

        ServiceResponse<User> response = validator.validateUser(user);

        if(!response.success) return new ServiceResponse<>(false, null, response.message);

        User saved = repository.save(user);
        return new ServiceResponse<>(saved);
    }

    public ServiceResponse<User> deleteById(long id){
        Optional<User> found = findById(id);

        if(found.isEmpty()) return new ServiceResponse<>(false, null, "User with id: " + id + " not found.");

        repository.deleteById(id);
        return new ServiceResponse<>(null);
    }

    public ServiceResponse<User> delete(User user){
        return deleteById(user.getId());
    }

}
