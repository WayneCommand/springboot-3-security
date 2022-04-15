package com.example.springboot3security;

import com.example.springboot3security.model.User;
import com.example.springboot3security.service.FusionUserService;
import com.example.springboot3security.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

// SPRING_PROFILES_ACTIVE=dev
@SpringBootTest
class Springboot3SecurityApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private FusionUserService fusionUserService;


	@Test
	void contextLoads() {
		User userById = userService.findUserById(1L);

		System.out.println(userById);

	}


	@Test
	void userIdt() {
		List<String> identifiers = fusionUserService.userIdentifiers(1L);

		System.out.println(identifiers);

	}


}
