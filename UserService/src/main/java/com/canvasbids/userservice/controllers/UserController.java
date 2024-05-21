package com.canvasbids.userservice.controllers;

import com.canvasbids.userservice.dto.*;
import com.canvasbids.userservice.entity.User;
import com.canvasbids.userservice.exception.IncorrectPasswordException;
import com.canvasbids.userservice.exception.InvalidCredentialsFormat;
import com.canvasbids.userservice.exception.UserAlreadyExistsException;
import com.canvasbids.userservice.exception.UserDoesNotExistException;
import com.canvasbids.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {


    // UserName == Email == Id

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationDTO request) {
        try {
            String token = userService.register(request);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 1);
            map.put("message", "User registration successful.");
            map.put("token", token);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (UserAlreadyExistsException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("status", 0);
            map.put("message", "User registration failed.");
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.CONFLICT);
        } catch (InvalidCredentialsFormat e) {
            Map<String, Object> map = new HashMap<>();
            map.put("status", 0);
            map.put("message", "User registration failed.");
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO request) {
        try {
            String token = userService.login(request);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 1);
            map.put("message", "User login successful.");
            map.put("token", token);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("status", 0);
            map.put("message", "User login failed.");
            map.put("error", "Incorrect username or password.");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO request, @RequestHeader("loggedInUsername") String username) {
        try {
            String msg = userService.updatePassword(request, username);
            Map<String, Object> map = new HashMap<>();
            map.put("status", 1);
            map.put("message", msg);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (IncorrectPasswordException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("status", 0);
            map.put("message", "User login failed.");
            map.put("error", "Incorrect password.");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
    }


    @PatchMapping("/picture")
    public ResponseEntity<?> changePicture(@Valid @RequestBody UpdatePictureDTO request, @RequestHeader("loggedInUsername") String username) {

        String msg = userService.updatePicture(request, username);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", msg);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PatchMapping("/name")
    public ResponseEntity<?> changeName(@Valid @RequestBody UpdateNameDTO request, @RequestHeader("loggedInUsername") String username) {

        String msg = userService.updateName(request, username);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", msg);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/sendRequest/{receiver}")
    public ResponseEntity<?> sendReqest(@PathVariable("receiver") String receiver, @RequestHeader("loggedInUsername") String sender) {

        userService.sendConnectionRequest(sender, receiver);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", "Connection request sent successfully.");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/acceptRequest/{requestId}")
    public ResponseEntity<?> acceptRequest(@PathVariable("requestId") String requestId, @RequestHeader("loggedInUsername") String receiver) {

        userService.acceptConnectionRequest(requestId, receiver);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", "Connection request accepted successfully.");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @GetMapping("/userDetails")
    public ResponseEntity<?> getUserDetails(@RequestHeader("loggedInUsername") String userId) {
        User user = userService.getUserDetails(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 1);
        map.put("message", "Success.");
        map.put("data", GetUserDTO.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .picture(user.getPicture())
                        .createdOn(user.getCreatedOn())
                        .updatedOn(user.getUpdatedOn())
                .build());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    //To be used by other services to validate token
    @GetMapping("/isValid/{token}")
    public boolean isValid(@PathVariable("token") String token) {
        return userService.validateToken(token);
    }

    //To be used by other services to get user details from username.
    //Properties: "name", "picture"
    @GetMapping("/nameAndPicture/{username}")
    public Map<String, String> getNameAndPicture(@PathVariable("username") String username) {
        return userService.getNameAndPicture(username);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalServerError(Exception e) {
        e.printStackTrace();
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        map.put("message", "Operation failed.");
        map.put("error", "Internal server error.");
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<?> userDoesNotExistException(UserDoesNotExistException e) {
        e.printStackTrace();
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        map.put("message", "Operation failed.");
        map.put("error", "User does not exist.");
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);
        map.put("message", "Operation failed.");
        map.put("error", "Some fields are empty. Please check the input.");
        return new ResponseEntity<>(map, HttpStatus.NOT_ACCEPTABLE);
    }
}
