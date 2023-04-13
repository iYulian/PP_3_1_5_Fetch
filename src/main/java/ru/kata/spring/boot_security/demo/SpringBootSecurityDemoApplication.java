package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	private final UserService userService;

	public SpringBootSecurityDemoApplication(UserService userService) {
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
	}

	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			if (userService.getFirstUserByEmail("admin@mail.ru") == null) {
				User user1 = new User("admin", "admin", (byte) 100, "admin@mail.ru", "admin");
				user1.userAddAuthority("ROLE_ADMIN");
				user1.userAddAuthority("ROLE_USER");
				userService.saveUser(user1);
			}
			if (userService.getFirstUserByEmail("user@mail.ru") == null) {
				User user2 = new User("user", "user", (byte) 1, "user@mail.ru", "user");
				user2.userAddAuthority("ROLE_USER");
				userService.saveUser(user2);
			}
		};
	}
}

