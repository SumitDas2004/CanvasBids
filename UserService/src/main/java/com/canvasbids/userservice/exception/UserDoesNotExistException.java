package com.canvasbids.userservice.exception;

public class UserDoesNotExistException extends RuntimeException{
    public UserDoesNotExistException(){
        super("User does not exist.");
    }

}
