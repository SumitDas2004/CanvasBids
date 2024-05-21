package com.canvasbids.bid.exception;

public class ChatDoesNotExistException extends Exception {
    public ChatDoesNotExistException() {
        super("Chat does not exist.");
    }
}
