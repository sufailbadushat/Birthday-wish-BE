package com.eous.events.controller;

import com.eous.events.dto.request.LoginReqDto;
import com.eous.events.dto.response.LoginResDto;
import com.eous.events.service.jwt.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authenticationService;

    @PostMapping("/signIn")
    public ResponseEntity<LoginResDto> signIn(@RequestBody LoginReqDto signInDtoReq) throws Exception {
        LoginResDto loginResDto = authenticationService.signIn(signInDtoReq);
        return new ResponseEntity<>(loginResDto, HttpStatus.OK);
    }
}
