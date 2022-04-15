package com.example.springboot3security.security;

import com.example.springboot3security.service.FusionUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonUserDetailsService implements UserDetailsService {

    private final FusionUserService fusionUserService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.example.springboot3security.model.User _user = fusionUserService.findUserByName(username);

        List<String> identifiers = fusionUserService.userIdentifiers(_user.getUserId());

        List<SimpleGrantedAuthority> authorities = identifiers.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return User.withUsername(username)
                .password(_user.getPassword())
                .authorities(authorities)
                .disabled(false)
                .build();


    }

}
