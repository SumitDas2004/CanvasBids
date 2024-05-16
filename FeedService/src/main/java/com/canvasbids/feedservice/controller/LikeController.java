package com.canvasbids.feedservice.controller;

import com.canvasbids.feedservice.dto.LikeDTO;
import com.canvasbids.feedservice.exception.PostDoesNotExistException;
import com.canvasbids.feedservice.service.LikeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LikeController {

    @Autowired
    LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<?> like(@Valid @RequestBody LikeDTO request, @RequestHeader("loggedInUsername") String username) throws PostDoesNotExistException {
        String msg = likeService.like(request, username);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", msg);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/like")
    public ResponseEntity<?> like(@RequestParam("postId") String postId, @RequestParam("offset") int offset, @RequestParam("size") int size) throws PostDoesNotExistException {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", "Success.");
        map.put("data", likeService.getLikes(postId, offset, size));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalServerError(Exception e){
        System.out.println(e.toString());e.printStackTrace();
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        map.put("message", "Internal Server Error.");
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        map.put("message", "Operation failed.");
        map.put("error", "Some fields are empty. Please check the input.");
        return new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);
    }
}
