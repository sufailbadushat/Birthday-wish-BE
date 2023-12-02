package com.eous.events.dto.response;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResDto {

    private long id;
    private String name;
    private String email;
    private String dob;

}
