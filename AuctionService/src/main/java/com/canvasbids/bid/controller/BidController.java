package com.canvasbids.bid.controller;

import com.canvasbids.bid.dto.BidRequestDTO;
import com.canvasbids.bid.service.BidService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BidController {
    @Autowired
    BidService bidService;

    @PostMapping("/bid")
    public ResponseEntity<?> doBid(@Valid @RequestBody BidRequestDTO request, @RequestHeader("loggedInUsername") String username){
        bidService.doBid(request, username);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", "Bid successful.");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
