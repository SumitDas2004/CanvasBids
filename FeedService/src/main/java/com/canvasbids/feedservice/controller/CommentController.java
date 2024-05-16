package com.canvasbids.feedservice.controller;

import com.canvasbids.feedservice.dto.CommentDTO;
import com.canvasbids.feedservice.dto.UpdateCommentDTO;
import com.canvasbids.feedservice.exception.PostDoesNotExistException;
import com.canvasbids.feedservice.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<?> comment(@Valid @RequestBody CommentDTO request, @RequestHeader("loggedInUsername") String username) throws PostDoesNotExistException {
        String msg = commentService.comment(request, username);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", msg);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/comment")
    public ResponseEntity<?> updateComment(@Valid @RequestBody UpdateCommentDTO request) throws PostDoesNotExistException {
        String msg = commentService.updateComment(request);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", msg);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") long commentId) throws PostDoesNotExistException {
        String msg = commentService.deleteComment(commentId);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", msg);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/comment")
    public ResponseEntity<?> getCommentById(@RequestParam(name="postId") String postId, @RequestParam(name="offset") int offset, @RequestParam(name="size") int size){
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", "Success.");
        map.put("data", commentService.findCommentsByPost(postId, offset, size));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        map.put("message", "Operation failed.");
        map.put("error", "Some fields are empty. Please check the input.");
        return new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalServerError(Exception e){
        System.out.println(e.toString());e.printStackTrace();
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        map.put("message", "Internal Server Error.");
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
