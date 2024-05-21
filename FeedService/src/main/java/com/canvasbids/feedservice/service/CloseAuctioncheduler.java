package com.canvasbids.feedservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CloseAuctioncheduler {
    @Autowired
    PostService postService;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void closeAuctions(){
        postService.closeAuction();
    }
}
