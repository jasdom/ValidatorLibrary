package com.jasdom.user_module.user_module.controller;

import com.jasdom.user_module.user_module.error.ErrorInfo;
import com.jasdom.user_module.user_module.model.User;
import com.jasdom.user_module.user_module.service.ServiceResponse;
import com.jasdom.user_module.user_module.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UserRestController {

    @Autowired
    UserService service;

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<User>> usersJson() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping(path="/{userId}", produces = {"application/json"})
    public ResponseEntity<User> userById(@PathVariable long userId) {
        Optional<User> found = service.findById(userId);
        if (found.isEmpty()) return ResponseEntity.notFound().build();

        return new ResponseEntity<>(found.get(), HttpStatus.OK);
    }

    @PostMapping(produces = {"application/json"})
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        if(service.findById(user.getId()).isPresent()) return ResponseEntity.status(HttpStatus.CONFLICT).build();
        ServiceResponse<User> response = service.add(user);

        if(!response.success){
            return ResponseEntity.badRequest().body(new ErrorInfo(response.message));
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.data.getId()).toUri();
        return ResponseEntity.created(location).body(response.data);
    }

    @PutMapping(path="/{userId}", produces = {"application/json"})
    public ResponseEntity<Object> updateUser(@PathVariable long userId, @RequestBody User user) {
        user.setId(userId);
        if(service.findById(user.getId()).isEmpty()) return ResponseEntity.badRequest().build();

        ServiceResponse<User> response = service.update(user);

        if(!response.success){
            return ResponseEntity.badRequest().body(new ErrorInfo(response.message));
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(response.data.getId()).toUri();
        return ResponseEntity.created(location).body(response.data);
    }

    @DeleteMapping(path="/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable int userId){
        ServiceResponse<User> response = service.deleteById(userId);
        if(!response.success) {
            return ResponseEntity.badRequest().body(new ErrorInfo(response.message));
        }
        return ResponseEntity.noContent().build();
    }
}
