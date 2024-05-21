package com.canvasbids.bid.dao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@FeignClient(name="FEED")
public interface PostFeignDao {

    @GetMapping("/isValidBid/{postId}/{amount}")
    boolean isValidBid(@PathVariable("postId") String postId, @PathVariable("amount") long amount);


    @PutMapping("/updateTimer/{postId}")
    void updateTimer(@PathVariable String postId);
}
