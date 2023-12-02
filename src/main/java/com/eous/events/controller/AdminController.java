package com.eous.events.controller;

import com.eous.events.service.AdminService;
import com.eous.events.service.jwt.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllEmployees() {
        return new ResponseEntity<>( adminService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/birthday")
    public ResponseEntity<?> getBirthdayEmp() {
        return new ResponseEntity<>( adminService.getBirthDayEmp(), HttpStatus.OK);
    }
}
