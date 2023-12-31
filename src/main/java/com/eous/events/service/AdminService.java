package com.eous.events.service;

import com.eous.events.dto.response.LoginResDto;
import com.eous.events.dto.response.UserResDto;
import com.eous.events.entity.Person;
import com.eous.events.entity.Role;
import com.eous.events.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final PersonRepository personRepository;

    public List<UserResDto> getAll() {
        return personRepository.findAll()
                .stream()
                .filter(person -> person.getRole().equals(Role.USER))
                .map(this::modelToDtoConverter)
                .collect(Collectors.toList());
    }

    public List<UserResDto> getBirthDayEmp() {

//        LocalDate currentDate = LocalDate.now();
//        .filter(person -> Role.USER.equals(person.getRole()) &&
//                person.getDob().getMonth() == currentDate.getMonth() &&
//                person.getDob().getDayOfMonth() == currentDate.getDayOfMonth())

        return personRepository.findAllByDob()
                .stream()
                .filter(person -> Role.USER.equals(person.getRole()))
                .map(this::modelToDtoConverter)
                .collect(Collectors.toList());
    }

    public UserResDto modelToDtoConverter(Person person) {
        UserResDto userResDto = new UserResDto();
        userResDto.setId(person.getId());
        userResDto.setName(person.getName());
        userResDto.setEmail(person.getEmail());
        userResDto.setJobTitle(person.getJobTitle());
        userResDto.setDob(String.valueOf(person.getDob()));
        return userResDto;
    }

}
