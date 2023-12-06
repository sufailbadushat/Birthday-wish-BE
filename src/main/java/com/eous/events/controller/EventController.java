package com.eous.events.controller;


import com.eous.events.service.EventService;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sse")
@Slf4j
public class EventController {

    private final EventService eventService;

    //@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping(value = "/subscribe", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe(@RequestParam("userId") long userId) {
        return eventService.subscribeServer(userId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/sendEvent")
    public ResponseEntity<?> dispatchEvent(@RequestParam("userId") long userId, @RequestParam(value = "desc", required = false) String description) {

        String msg = eventService.sendEvent(userId, description);

        return msg.equals("Success!") ? new ResponseEntity<>(msg, HttpStatus.OK) : new ResponseEntity<>("Employee is not subscribed!", HttpStatus.OK);

    }
}
