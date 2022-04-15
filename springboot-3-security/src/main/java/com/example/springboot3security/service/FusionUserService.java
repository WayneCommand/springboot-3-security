package com.example.springboot3security.service;

import com.example.springboot3security.model.Resource;
import com.example.springboot3security.model.RoleResource;
import com.example.springboot3security.model.UserRole;
import com.example.springboot3security.repository.*;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record FusionUserService(
        ResourceRepository resourceRepository,
        RoleRepository roleRepository,
        RoleResourceRepository roleResourceRepository,
        UserRepository userRepository,
        UserRoleRepository userRoleRepository
) {


    public List<String> userIdentifiers(Long userId) {

        Streamable<UserRole> userRoleList = userRoleRepository.findByUserId(userId);

        List<Long> roleIdList = userRoleList
                .map(UserRole::getRoleId)
                .toList();

        Streamable<RoleResource> roleResourceList = roleResourceRepository.findByRoleIdIn(roleIdList);

        List<Long> resIdList = roleResourceList
                .map(RoleResource::getResId)
                .toList();

        Streamable<Resource> resourceList = resourceRepository.findByResIdIn(resIdList);

        return resourceList.stream()
                .map(Resource::getIdentifier)
                .toList();
    }

}
