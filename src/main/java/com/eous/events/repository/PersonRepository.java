package com.eous.events.repository;

import com.eous.events.entity.Person;
import com.eous.events.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByRole(Role role);

    Optional<Person> findByEmail(String email);
}
