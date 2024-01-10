package com.securityJava.springsecurity;

import com.securityJava.springsecurity.entities.Role;
import com.securityJava.springsecurity.entities.User;
import com.securityJava.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringsecurityApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringsecurityApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		User adminAccount= userRepository.findByRole(Role.ADMIN);

		if (adminAccount==null){
			User user= new User();
			user.setFirstName("adminfirstName");
			user.setLastName("adminelastname");
			user.setEmail("admin@admin.admin");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			user.setRole(Role.ADMIN);
			userRepository.save(user);
		}
	}
}
