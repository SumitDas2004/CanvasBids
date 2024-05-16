package com.canvasbids.userservice.exception;

public class UserAlreadyExistsException extends  Exception{
    public UserAlreadyExistsException(){
        super("User with the given Email id exists already.");
    };
}
