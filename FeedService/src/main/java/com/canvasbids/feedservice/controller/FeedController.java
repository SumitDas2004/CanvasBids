package com.canvasbids.feedservice.controller;

import com.canvasbids.feedservice.dto.CreatePostDTO;
import com.canvasbids.feedservice.dto.UpdatePostDTO;
import com.canvasbids.feedservice.exception.PostDoesNotExistException;
import com.canvasbids.feedservice.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FeedController {
    @Autowired
    PostService postService;

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostDTO request, @RequestHeader("loggedInUsername") String username){
        System.out.println(username);
        String msg = postService.createPost(request, username);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", msg);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/post")
    public ResponseEntity<?> updatePost(@Valid @RequestBody UpdatePostDTO request){
        String msg = postService.updatePost(request);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", msg);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("postId") String postId){
        String msg = postService.deletePost(postId);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", msg);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllPost(@RequestParam(name="offset") int offset, @RequestParam(name="size") int size) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", "Success.");
        map.put("data", postService.fetchPosts(offset, size));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }



    @ExceptionHandler(PostDoesNotExistException.class)
    public ResponseEntity<?> postDoesNotExist(PostDoesNotExistException e){
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        map.put("message", e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
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
