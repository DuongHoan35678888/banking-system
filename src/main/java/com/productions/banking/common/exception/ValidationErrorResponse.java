package com.productions.banking.common.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorResponse(

        LocalDateTime timestamp,

        int status,

        String error,

        Map<String, String> fieldErrors,

        String path
) {
}