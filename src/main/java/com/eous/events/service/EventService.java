package com.eous.events.service;

import com.eous.events.dto.response.BirthdayWish;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    Map<Long, SseEmitter> emitters = new HashMap<>();

    public SseEmitter subscribeServer(long userId) {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sendInitialMessage(sseEmitter, userId);

        emitters.put(userId, sseEmitter);

        sseEmitter.onCompletion(() -> handleCompletion(userId, sseEmitter));
        sseEmitter.onTimeout(() -> handleTimeout(userId, sseEmitter));
        sseEmitter.onError((e) -> handleError(userId, sseEmitter, e));

        return sseEmitter;
    }

    public String sendEvent(long userId, String desc) {
        SseEmitter emitter = emitters.get(userId);

        log.info(desc + " desc value");
// Admin can pass without description so, it will be set as description
        String title = "Happy Birthday !";
        if (desc == null || desc.isEmpty()) {
            desc = "We hope your special day will bring you lots of happiness, love, and fun. You deserve them a lot. Enjoy! Hope your day goes great!";
        }


// Create new Birthday wish pojo dto class obj and set title and description
        BirthdayWish wish = new BirthdayWish();
        wish.setTitle(title);
        wish.setDescription(desc);


        try {
            if (emitter != null) {
                emitter.send(SseEmitter.event().name("wish").data(wish));
                return "Success!";
            }
        } catch (IOException ex) {

            log.error("Error sending event for user {} - Exception: {}", userId, ex.getMessage());
            emitter.complete();
            emitters.remove(userId);
        }
        return "Employee with id: "+userId+" is not subscribed!";

    }

    private void sendInitialMessage(SseEmitter emitter, Long userId) {
        try {
            emitter.send(SseEmitter.event().name("INIT").data("Subscription message for emp with id: " + userId));
        } catch (IOException e) {
            log.error("Error sending initialization message:{}", e.getMessage());
        }
    }

    private void handleCompletion(long userId, SseEmitter sseEmitter) {
        emitters.remove(userId);
    }

    private void handleTimeout(long userId, SseEmitter sseEmitter) {
        emitters.remove(userId);
    }

    private void handleError(long userId, SseEmitter sseEmitter, Throwable e) {
        emitters.remove(userId);
    }

}
