package com.eous.events.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        log.error("Unauthorized error: {}", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(
                (response.getStatus()) == HttpServletResponse.SC_FORBIDDEN ?
                        HttpServletResponse.SC_FORBIDDEN : HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = getObjectMap(request, response, authException);
        new ObjectMapper().writeValue(response.getOutputStream(), body);

        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

    }

    private static Map<String, Object> getObjectMap(HttpServletRequest request, HttpServletResponse response,
                                                    AuthenticationException authException) {
        final Map<String, Object> body = new HashMap<>();
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        if (response.getStatus() == HttpServletResponse.SC_FORBIDDEN) {
            body.put("status", HttpServletResponse.SC_FORBIDDEN);
            body.put("error", "Forbidden");

        } else {
            body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            body.put("error", "Unauthorized");
        }
        return body;
    }

}
