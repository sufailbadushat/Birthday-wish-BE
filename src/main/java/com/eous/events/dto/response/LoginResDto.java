package com.eous.events.dto.response;

import com.eous.events.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Locale;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResDto {

    private long id;
    private String name;
    private String email;
    private String dob;
    private String jobTitle;
    private Role role;
    private String token;
}
