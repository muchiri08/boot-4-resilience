package com.muchiri.boot_4_pg;

import org.jspecify.annotations.Nullable;
import org.springframework.core.retry.RetryException;
import org.springframework.core.retry.RetryListener;
import org.springframework.core.retry.RetryPolicy;
import org.springframework.core.retry.RetryState;
import org.springframework.core.retry.Retryable;
import org.springframework.stereotype.Component;

@Component
public class NotificationsRetryListener implements RetryListener {

    @Override
    public void onRetryFailure(RetryPolicy retryPolicy, Retryable<?> retryable, Throwable throwable) {
        if (retryable instanceof NotificationRetryable notification) {
            IO.println("on retry failure for message: " + notification.notification().message());
        } else {
            IO.println("on retry failuree");
        }
    }

    @Override
    public void onRetryPolicyExhaustion(RetryPolicy retryPolicy, Retryable<?> retryable, RetryException exception) {
        if (retryable instanceof NotificationRetryable notification) {
            IO.println("after all retrues have failed for message: " + notification.notification().message());
        } else {
            IO.println("after all retrues have failed");
        }
    }

    @Override
    public void onRetrySuccess(RetryPolicy retryPolicy, Retryable<?> retryable, @Nullable Object result) {
        if (retryable instanceof NotificationRetryable notification) {
            IO.println("on retry success for message: " + notification.notification().message());
        } else {
            IO.println("on retry success");
        }
    }

    @Override
    public void onRetryableExecution(RetryPolicy retryPolicy, Retryable<?> retryable, RetryState retryState) {
        if (retryable instanceof NotificationRetryable notification) {
            IO.println("Retry count for message: " + notification.notification().message());
        } else {
            IO.println("Retry count: " + retryState.getRetryCount());
        }
    }

}
