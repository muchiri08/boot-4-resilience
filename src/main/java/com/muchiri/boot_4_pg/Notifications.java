package com.muchiri.boot_4_pg;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.retry.RetryPolicy;
import org.springframework.core.retry.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
class Notifications {

    private final RetryTemplate retryTemplate;
    private final NotificationsRetryListener notificationsRetryListener;

    public Notifications(NotificationsRetryListener listener) {
        notificationsRetryListener = listener;

        var retryPolicy = RetryPolicy.builder()
                .maxRetries(3)
                .delay(Duration.ofSeconds(2))
                .multiplier(1.5)
                .maxDelay(Duration.ofSeconds(10))
                .includes(NotificationException.class)
                .build();

        retryTemplate = new RetryTemplate(retryPolicy);
        retryTemplate.setRetryListener(notificationsRetryListener);
    }

    public void send(Notification notification) {
        try {
            retryTemplate.execute(new NotificationRetryable(new Notification("Hello there")));
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static class NotificationException extends RuntimeException {
        public NotificationException(String message) {
            super(message);
        }
    }

    @RestController
    @RequestMapping("api")
    public static class NotificationController {
        @Autowired
        Notifications notifications;

        @GetMapping
        public void test() {
            notifications.send(new Notification("Hello there"));
        }
    }

}