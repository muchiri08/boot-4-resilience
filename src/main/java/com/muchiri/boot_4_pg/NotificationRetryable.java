package com.muchiri.boot_4_pg;

import org.springframework.core.retry.Retryable;

import com.muchiri.boot_4_pg.Notifications.NotificationException;

public class NotificationRetryable implements Retryable<Void> {

    private final Notification notification;

    public NotificationRetryable(Notification notification) {
        this.notification = notification;
    }

    @Override
    public Void execute() throws Throwable {
        // IO.print("sending notification with message: " + notification.message());
        // return null;
        throw new NotificationException("thrownnnnnnnnnnn");
    }

    public Notification notification() {
        return this.notification;
    }
    
}