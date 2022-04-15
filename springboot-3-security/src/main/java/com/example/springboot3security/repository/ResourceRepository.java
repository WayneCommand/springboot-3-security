package com.example.springboot3security.repository;

import com.example.springboot3security.model.Resource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends CrudRepository<Resource, Long> {


    Streamable<Resource> findByResIdIn(List<Long> idList);

    Streamable<Resource> findByIdentifierIn(List<String> idList);

}
