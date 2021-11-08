package com.jasdom.user_module.user_module.error;

import java.time.LocalDateTime;

public class ErrorInfo {
    public final LocalDateTime time;
    public final String message;

    public ErrorInfo(String message) {
        this.message = message;
        time = LocalDateTime.now();
    }
}
