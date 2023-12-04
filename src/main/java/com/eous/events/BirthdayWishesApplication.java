package com.eous.events;

import com.eous.events.entity.Person;
import com.eous.events.entity.Role;
import com.eous.events.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
public class BirthdayWishesApplication implements CommandLineRunner {

	@Autowired
	PersonRepository personRepository;

	public static void main(String[] args) {
		SpringApplication.run(BirthdayWishesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Person adminUser = personRepository.findByRole(Role.ADMIN);
		if (adminUser == null) {
			Person person = new Person();
			person.setEmail("admin@gmail.com");
			person.setName("admin");
			person.setJobTitle("Sr. Hr");
			person.setDob(LocalDate.now());
			person.setRole(Role.ADMIN);
			person.setPassword(new BCryptPasswordEncoder().encode("admin"));
			personRepository.save(person);
		}
	}
}
