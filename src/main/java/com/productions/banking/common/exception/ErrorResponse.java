package com.productions.banking.common.exception;

import java.time.LocalDateTime;

public record ErrorResponse(

        LocalDateTime timestamp,

        int status,

        String error,

        String message,

        String path
) {
}