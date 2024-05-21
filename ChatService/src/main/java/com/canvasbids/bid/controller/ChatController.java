package com.canvasbids.bid.controller;

import com.canvasbids.bid.exception.ChatDoesNotExistException;
import com.canvasbids.bid.service.ChatService;
import com.canvasbids.bid.dto.SendMessageDTO;
import com.canvasbids.bid.dto.UpdateMessageDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ChatController {
    @Autowired
    ChatService chatService;

    @PostMapping("/message")
    public ResponseEntity<?> sendMessage(@RequestHeader("loggedInUsername")String sender, @Valid @RequestBody SendMessageDTO request) throws ChatDoesNotExistException {
        try {
            Date creationTime = chatService.sendMessage(sender, request.getReceiver(), request.getMessageBody());
            Map<String, Object> map = new HashMap<>();
            map.put("status", 1);
            map.put("message", "Success");
            map.put("creationTime", creationTime);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch (ChatDoesNotExistException e){
            Map<String, Object> map = new HashMap<>();
            map.put("status", 1);
            map.put("message", "failed");
            map.put("error", "Chat does not exist.");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/message")
    public ResponseEntity<?> updateMessage(@RequestHeader("loggedInUsername")String sender, @Valid @RequestBody UpdateMessageDTO request) throws ChatDoesNotExistException {

            chatService.updateMessage(request.getChatId(), sender, request.getCreatedAt(), request.getMessageBody());
            Map<String, Object> map = new HashMap<>();
            map.put("status", 1);
            map.put("message", "Success");
            return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/message")
    public ResponseEntity<?> getAllMessages(@RequestParam(name="offset", required = true) int offset, @RequestParam(name="size", required = true) int size, @RequestParam(name="chatId", required = true) String chatId) throws ChatDoesNotExistException {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", "Success");
        map.put("data", chatService.getAllMessages(chatId, offset, size));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/getChats")
    public ResponseEntity<?> getAllChats(@RequestHeader("loggedInUsername")String user, @RequestParam(name = "offset", required = true) int offset, @RequestParam(name="size", required = true) int size) throws ChatDoesNotExistException {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", "Success");
        map.put("data", chatService.getAllChats(user, offset, size));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //to be used by user service for creating chat
    @PostMapping("/create")
    public void createChat(@RequestBody Map<String, String> map){
        chatService.createChat(map);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalServerlError(Exception e){
        e.printStackTrace();
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", "failed");
        map.put("error", "Something went wrong.");
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
