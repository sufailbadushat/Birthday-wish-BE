package com.eous.events.service;

import com.eous.events.dto.response.LoginResDto;
import com.eous.events.dto.response.UserResDto;
import com.eous.events.entity.Person;
import com.eous.events.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final PersonRepository personRepository;

    public List<UserResDto> getAll() {
        return personRepository.findAll()
                .stream()
                .map(this::modelToDtoConverter)
                .collect(Collectors.toList());
    }

    public List<UserResDto> getBirthDayEmp() {
        return personRepository.findAllByDobIsCurrentDate()
                .stream()
                .map(this::modelToDtoConverter)
                .collect(Collectors.toList());
    }

    public UserResDto modelToDtoConverter(Person person) {
        UserResDto userResDto = new UserResDto();
        userResDto.setId(person.getId());
        userResDto.setName(person.getName());
        userResDto.setEmail(person.getEmail());
        userResDto.setDob(String.valueOf(person.getDob()));
        return userResDto;
    }

}
