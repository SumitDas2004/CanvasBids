package com.canvasbids.bid.controller;

import com.canvasbids.bid.dto.BidRequestDTO;
import com.canvasbids.bid.entity.BidId;
import com.canvasbids.bid.exception.InvalidBidException;
import com.canvasbids.bid.service.BidService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class
BidController {
    @Autowired
    BidService bidService;

    @PostMapping("/bid")
    public ResponseEntity<?> doBid(@Valid @RequestBody BidRequestDTO request, @RequestHeader("loggedInUsername") String username){
        try {
            bidService.doBid(request, username);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 1);
            map.put("message", "Bid successful.");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch (InvalidBidException e){
            Map<String, Object> map = new HashMap<>();
            map.put("status", 0);
            map.put("message", "Bid failed.");
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/bid")
    public ResponseEntity<?> getBid(@RequestParam("offset") int offset, @RequestParam("size") int size, @RequestParam("postId") String post, @RequestHeader("loggedInUsername") String username){
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", "Successful.");
        map.put("data", bidService.getBid(offset, size, post, username));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @PatchMapping("/declareWinner")
    public ResponseEntity<?> declareWinner(@RequestParam("userId") String user, @RequestParam("postId") String post, @RequestHeader("loggedInUsername") String username){
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", "Successful.");
        bidService.declareWinner(BidId.builder().userId(user).postId(post).build());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
