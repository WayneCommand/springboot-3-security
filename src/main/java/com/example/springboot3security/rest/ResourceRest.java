package com.example.springboot3security.rest;

import com.example.springboot3security.model.Resource;
import com.example.springboot3security.repository.ResourceRepository;
import com.example.springboot3security.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/resource")
@RequiredArgsConstructor
public class ResourceRest {

    private final ResourceRepository resourceRepository;


    // https://www.baeldung.com/spring-security-method-security
    @PreAuthorize("hasPermission('project','project::view')")
    @GetMapping
    public Stream<Resource> resources() {
        User currentUser = SecurityUtil.getCurrentUser();
        List<String> authorities = currentUser.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .toList();

        return resourceRepository.findByIdentifierIn(authorities).stream();
    }

    @PreAuthorize("hasPermission('project','project::delete')")
    @DeleteMapping
    public void delete() {

        System.out.println("deleted.");
    }


}
