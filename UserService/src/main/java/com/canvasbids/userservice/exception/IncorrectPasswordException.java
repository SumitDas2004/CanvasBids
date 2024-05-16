package com.canvasbids.userservice.exception;

public class IncorrectPasswordException extends Exception {
    public IncorrectPasswordException(){
        super("Incorrect Password.");
    }
}
