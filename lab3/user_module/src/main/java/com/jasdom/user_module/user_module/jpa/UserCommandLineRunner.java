package com.jasdom.user_module.user_module.jpa;

import com.jasdom.user_module.user_module.model.User;
import com.jasdom.user_module.user_module.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCommandLineRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User("Jonas", "Jonaitis", "jonas@gmail.com", "+37014758315", "TestPasswor-d122", "Vilnius"));
        userRepository.save(new User("Tomas", "Tomaitis", "tomas@gmail.com", "+37014758715", "TestPasswor-d122", "Kaunas"));
        userRepository.save(new User("Petras", "Petraitis", "petras@gmail.com", "+37074758315", "TestPasswor-d122", "Jurbarkas"));
    }
}
