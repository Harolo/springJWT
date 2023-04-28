package com.springJWT.Utils;

import org.springframework.http.HttpStatus;

public class MessageResponse {

    private String message;
    private HttpStatus http;

    public MessageResponse(String message) {
        this.message = message;
    }

    public MessageResponse(String message, HttpStatus http) {
        this.message = message;
        this.http = http;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttp() {
        return http;
    }

    public void setHttp(HttpStatus http) {
        this.http = http;
    }
}
