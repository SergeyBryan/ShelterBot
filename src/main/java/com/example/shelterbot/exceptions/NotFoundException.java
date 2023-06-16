package com.example.shelterbot.exceptions;

public class NotFoundException extends Exception {

    private final String message;

    public NotFoundException(String str) {
        message = str;
    }

    @Override
    public String toString() {
        return "NotFoundException{" +
                "message='" + message + '\'' +
                '}';
    }
}
