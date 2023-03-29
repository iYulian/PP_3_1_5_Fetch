package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	@Autowired
	private UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
	}

	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			if (userService.getFirstUserByName("admin") == null) {
				userService.saveUser(new User("admin", "admin", (byte) 100),
						"ROLE_ADMIN", "ROLE_USER");
			}
			if (userService.getFirstUserByName("user") == null) {
				userService.saveUser(new User("user", "user", (byte) 1),
						"", "ROLE_USER");
			}
		};
	}
}

