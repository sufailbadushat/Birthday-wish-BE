package com.eous.events;

import com.eous.events.entity.Person;
import com.eous.events.entity.Role;
import com.eous.events.repository.PersonRepository;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class BirthdayWishesApplication implements CommandLineRunner {

    @Autowired
    PersonRepository personRepository;

    String passwordForAll = new BCryptPasswordEncoder().encode("admin");
    public static void main(String[] args) {
        SpringApplication.run(BirthdayWishesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (personRepository.count() == 0) {

            System.out.println("logging: \n where \n it's \n working ");

            List<Person> personList = Arrays.asList(
                    createPerson("Admin", "admin@gmail.com", "Senior HR", LocalDate.parse("1990-08-12"), Role.ADMIN),

                    createPerson("Sufail Badusha T", "sufail@gmail.com", "Java full stack", LocalDate.parse("2000-01-01"), Role.USER),
                    createPerson("Kabir Joshi", "kabir@gmail.com", "DevOps Engineer", LocalDate.parse("1986-12-23"), Role.USER),
                    createPerson("Sana Malik", "sana@gmail.com", "Front-end Developer", LocalDate.parse("1994-12-23"), Role.USER),
                    createPerson("Ethan Clark", "ethan@gmail.com", "DevOps Engineer", LocalDate.parse("1989-12-27"), Role.USER),
                    createPerson("Fatima Ahmed", "fatima@gmail.com", "Software Engineer", LocalDate.parse("1991-12-27"), Role.USER),

                    createPerson("Rahul T Ravi", "rahul@gmail.com", "Jr. Java Tester", LocalDate.parse("1990-12-24"), Role.USER),
                    createPerson("Anaya Patel", "anaya@gmail.com", "Business Analyst", LocalDate.parse("1993-12-24"), Role.USER),
                    createPerson("Ayaan Khan", "ayaan@gmail.com", "Mobile App Developer", LocalDate.parse("1990-12-28"), Role.USER),
                    createPerson("Grace Thompson", "grace@gmail.com", "Cloud Solutions Architect", LocalDate.parse("1981-12-28"), Role.USER),
                    createPerson("Omar Rahman", "omar@gmail.com", "Data Scientist", LocalDate.parse("1985-12-24"), Role.USER),

                    createPerson("Rockson Augestine", "rockson@gmail.com", "Jr. Java Developer", LocalDate.parse("1990-12-25"), Role.USER),
                    createPerson("Zainab Ali", "zainab@gmail.com", "QA Analyst", LocalDate.parse("1988-12-25"), Role.USER),
                    createPerson("Olivia Martinez", "olivia@gmail.com", "Mobile App Developer", LocalDate.parse("1993-12-25"), Role.USER),
                    createPerson("Aarav Sharma", "aarav@gmail.com", "Full Stack Developer", LocalDate.parse("1987-12-29"), Role.USER),
                    createPerson("William Johnson", "william@gmail.com", "QA Engineer", LocalDate.parse("1990-12-29"), Role.USER),

                    createPerson("Ava Taylor", "ava.taylor@gmail.com", "Business Analyst", LocalDate.parse("1994-12-30"), Role.USER),
                    createPerson("Emma Davis", "emma.d@gmail.com", "Systems Analyst", LocalDate.parse("1984-09-30"), Role.USER),
                    createPerson("James Wilson", "james.w@gmail.com", "Technical Writer", LocalDate.parse("1982-12-26"), Role.USER),
                    createPerson("Arjun Desai", "arjun.desai@gmail.com", "Database Administrator", LocalDate.parse("2000-12-26"), Role.USER),
                    createPerson("Ishika Reddy", "ishika.reddy@gmail.com", "UI/UX Designer", LocalDate.parse("1995-12-31"), Role.USER)
                    );
            personList.forEach(personRepository::save);
        }
    }

    public Person createPerson(String name, String email, String jobTitle, LocalDate dob, Role role) {
        Person person = new Person();
        person.setName(name);
        person.setEmail(email);
        person.setJobTitle(jobTitle);
        person.setPassword(passwordForAll); // All the users and admin password is "admin" for easy use
        person.setDob(dob);
        person.setRole(role);

        return person;
    }




}
