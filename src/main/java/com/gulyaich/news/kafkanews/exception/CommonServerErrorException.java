package com.gulyaich.news.kafkanews.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CommonServerErrorException extends RuntimeException {

    public CommonServerErrorException(String message) {
        super(message);
    }

    public CommonServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}