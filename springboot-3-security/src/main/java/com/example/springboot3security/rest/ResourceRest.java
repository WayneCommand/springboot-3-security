package com.example.springboot3security.rest;

import com.example.springboot3security.model.Resource;
import com.example.springboot3security.repository.ResourceRepository;
import com.example.springboot3security.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/resource")
@RequiredArgsConstructor
public class ResourceRest {

    private final ResourceRepository resourceRepository;


    // https://www.baeldung.com/spring-security-method-security
    // @Secured("project::create")
    @GetMapping
    public List<Resource> resources() {
        User currentUser = SecurityUtil.getCurrentUser();
        List<String> authorities = currentUser.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .toList();

        return resourceRepository.findByIdentifierIn(authorities)
                .toList();
    }

}
