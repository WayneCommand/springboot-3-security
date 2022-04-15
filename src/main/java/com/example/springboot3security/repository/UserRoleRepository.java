package com.example.springboot3security.repository;

import com.example.springboot3security.model.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

    Streamable<UserRole> findByUserId(Long id);

}
