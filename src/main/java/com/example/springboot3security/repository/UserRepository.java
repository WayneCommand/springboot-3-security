package com.example.springboot3security.repository;

import com.example.springboot3security.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByUserName(String name);

    User findByUserId(Long id);


}
