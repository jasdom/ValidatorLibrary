package com.jasdom.user_module.user_module.repository;

import com.jasdom.user_module.user_module.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {



}
