package com.example.springboot3security.service;

import com.example.springboot3security.model.Resource;
import com.example.springboot3security.model.RoleResource;
import com.example.springboot3security.model.User;
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
        UserRoleRepository userRoleRepository,
        UserService userService

) {


    public List<String> userIdentifiers(Long userId) {

        // 根据用户ID 查询 用户所有的 角色
        Streamable<UserRole> userRoleList = userRoleRepository.findByUserId(userId);

        List<Long> roleIdList = userRoleList
                .map(UserRole::getRoleId)
                .toList();

        // 根据角色IDs 查询 所有的 角色资源
        Streamable<RoleResource> roleResourceList = roleResourceRepository.findByRoleIdIn(roleIdList);

        List<Long> resIdList = roleResourceList
                .map(RoleResource::getResId)
                .toList();

        // 根据 资源ID 找到资源的详细记录
        Streamable<Resource> resourceList = resourceRepository.findByResIdIn(resIdList);

        return resourceList.stream()
                .map(Resource::getIdentifier)
                .toList();
    }

    public User findUserById(Long userId) {
        return userService.findUserById(userId);
    }

    public User findUserByName(String name) {
        return userService.findUserByName(name);
    }

}
