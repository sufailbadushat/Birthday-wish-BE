package com.eous.events.repository;

import com.eous.events.entity.Person;
import com.eous.events.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByRole(Role role);
    Optional<Person> findByEmail(String email);

    @Query("SELECT p FROM Person p WHERE MONTH(p.dob) = MONTH(CURRENT_DATE) AND DAY(p.dob) = DAY(CURRENT_DATE)")
    List<Person> findAllByDob();

}
