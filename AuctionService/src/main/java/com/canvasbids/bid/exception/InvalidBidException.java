package com.canvasbids.bid.exception;

public class InvalidBidException extends Exception{
    public InvalidBidException(){
        super("Invalid bid, check the minimum bid amount.");
    }
}
