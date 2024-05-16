package com.canvasbids.feedservice.exception;

public class PostDoesNotExistException extends Exception{
    public PostDoesNotExistException(){
        super("Post doesn't exist.");
    }
}
