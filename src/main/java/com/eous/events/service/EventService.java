package com.eous.events.service;

import com.eous.events.dto.response.BirthdayWish;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<Long, BirthdayWish> storeDataBeforeSubscribe = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public SseEmitter subscribeServer(long userId) {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);

        sendInitialMessage(sseEmitter, userId);  // send initial msg
        //sending stored msg - stored if admin sends msg before connection init
        scheduler.schedule(() -> sendQueuedMessage(sseEmitter, userId), 800, TimeUnit.MILLISECONDS);

        emitters.put(userId, sseEmitter);

        sseEmitter.onCompletion(() -> emitters.remove(userId));
        sseEmitter.onTimeout(() -> emitters.remove(userId));
        sseEmitter.onError((e) -> {emitters.remove(userId); log.info(e.getMessage());});
        return sseEmitter;
    }

    public void sendQueuedMessage(SseEmitter emitter, Long userId){
        if (storeDataBeforeSubscribe.containsKey(userId)) {
            try {
                emitter.send(SseEmitter.event().name("wish").data(storeDataBeforeSubscribe.get(userId)));
                storeDataBeforeSubscribe.remove(userId);
            } catch (IOException e) {
                log.error("Error sending sendQueuedMessage message:{}", e.getMessage());
            }
        }
    }

    public String sendEvent(long userId, String desc) {
        SseEmitter emitter = emitters.get(userId);

        BirthdayWish wish = new BirthdayWish();  // Create new Birthday wish pojo dto class obj and set title and description
        wish.setTitle("Happy Birthday !");
        // Admin can pass without description so, it will be set as description
        if (desc == null || desc.isEmpty()) desc = "We hope your special day will bring you lots of happiness, love, and fun. You deserve them a lot. Enjoy! Hope your day goes great!";
        wish.setDescription(desc);

        try {

            if ((emitter != null)) emitter.send(SseEmitter.event().name("wish").data(wish));
            else storeDataBeforeSubscribe.put(userId, wish);

        } catch (IOException ex) {
            log.error("Error sending event for user {} - Exception: {}", userId, ex.getMessage());
            if (ex.getMessage() != null && ex.getMessage().contains("An established connection was aborted by the software in your host machine")) {
                log.info("Emitter for userId {} is closed. Storing in storeDataBeforeSubscribe.", userId);
                storeDataBeforeSubscribe.put(userId, wish);
            }
            emitter.complete();
            emitters.remove(userId);
        }
        return "Success!";
    }

    private void sendInitialMessage(SseEmitter emitter, Long userId) {
        try {
            emitter.send(SseEmitter.event().name("INIT").data("Subscription message for emp with id: " + userId));
        } catch (IOException e) {
            log.error("Error sending initialization message:{}", e.getMessage());
        }
    }

}
