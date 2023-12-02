package com.eous.events.service.jwt;

import com.eous.events.dto.request.LoginReqDto;
import com.eous.events.dto.response.LoginResDto;
public interface AuthService {

    public LoginResDto signIn(LoginReqDto loginReqDto) throws Exception;

}
