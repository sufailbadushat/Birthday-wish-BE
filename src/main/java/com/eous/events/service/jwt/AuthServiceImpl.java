package com.eous.events.service.jwt;

import com.eous.events.dto.request.LoginReqDto;
import com.eous.events.dto.response.LoginResDto;
import com.eous.events.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final PersonRepository personRepository;
    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;


    @Override
    public LoginResDto signIn(LoginReqDto loginReqDto) throws Exception {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReqDto.getUsername(), loginReqDto.getPassword())
        );

        // System.out.println(authentication);
        // SecurityContextHolder.getContext().setAuthentication(authentication);

        var person = personRepository.findByEmail(loginReqDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password !"));

        String jwt = jwtService.generateToken(person);

        LoginResDto loginResDto = new LoginResDto();
        loginResDto.setId(person.getId());
        loginResDto.setName(person.getName());
        loginResDto.setEmail(person.getEmail());
        loginResDto.setRole(person.getRole());
        loginResDto.setJobTitle(person.getJobTitle());
        loginResDto.setDob(String.valueOf(person.getDob()));
        loginResDto.setToken(jwt);

        return loginResDto;


    }


}
