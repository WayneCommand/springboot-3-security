package com.example.springboot3security.repository;

import com.example.springboot3security.model.RoleResource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleResourceRepository extends CrudRepository<RoleResource, Long> {

    Streamable<RoleResource> findByRoleIdIn(List<Long> idList);

}
