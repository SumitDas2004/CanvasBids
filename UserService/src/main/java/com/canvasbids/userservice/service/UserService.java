package com.canvasbids.userservice.service;

import com.canvasbids.userservice.dao.UserDao;
import com.canvasbids.userservice.dto.*;
import com.canvasbids.userservice.entity.User;
import com.canvasbids.userservice.exception.IncorrectPasswordException;
import com.canvasbids.userservice.exception.InvalidCredentialsFormat;
import com.canvasbids.userservice.exception.UserAlreadyExistsException;
import com.canvasbids.userservice.exception.UserDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserDao userdao;
    public String register(UserRegistrationDTO request) throws UserAlreadyExistsException, InvalidCredentialsFormat {

        if(invalidEmailFormat(request.getEmail()))
            throw new InvalidCredentialsFormat("Invalid email format.");
        if(existingUser(request.getEmail()))
            throw new UserAlreadyExistsException();
        User user = request.toUser();
        user.setPassword(encoder.encode(user.getPassword()));
        userdao.save(user);
        return jwtService.generateToken(request.getEmail());
    }

    public String login(UserLoginDTO request) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword(), new ArrayList<>()));
        return jwtService.generateToken(request.getEmail());
    }

    private boolean invalidEmailFormat(String email) {
        email = email.trim();
        String pattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return !email.matches(pattern);
    }


    private boolean existingUser(String email) {
        Optional<User> user= userdao.findById(email);
        return user.isPresent();
    }


    public boolean validateToken(String token){
        try {
            String username = jwtService.extractUserName(token);
            Optional<User> user = userdao.findById(username);
            if (user.isEmpty()) return false;
            return !jwtService.isExpired(token);
        }catch (Exception e){
            return false;
        }
    }

    public User getUserDetails(String userId) {
        Optional<User> optionalUser = userdao.findById(userId);
        if(optionalUser.isEmpty()){
            throw new UserDoesNotExistException();
        }
        return optionalUser.get();
    }


    public Map<String, String> getNameAndPicture(String username) {
        Optional<User> optionalUser = userdao.findById(username);
        if(optionalUser.isEmpty()){
            throw new UserDoesNotExistException();
        }
        User user = optionalUser.get();
        Map<String, String> map = new HashMap<>();
        map.put("name", user.getName());
        map.put("picture", user.getPicture());
        return map;
    }


    public String updatePassword(ChangePasswordDTO request, String username) throws IncorrectPasswordException {
        Optional<User> optionalUser = userdao.findById(username);
        if(optionalUser.isEmpty())throw new UserDoesNotExistException();
        User user = optionalUser.get();
        if(!encoder.matches(request.getOldPassword(), user.getPassword())) throw new IncorrectPasswordException();
        String encodedPassword = encoder.encode(request.getNewPassword());
        user.setPassword(encodedPassword);
        userdao.save(user);
        return "Successfully changed the password.";
    }

    public String updatePicture(UpdatePictureDTO request, String username){
        userdao.updatePicture(request.getPicture(), username);
        return "Updated picture successfully.";
    }

    public String updateName(UpdateNameDTO request, String username){
        userdao.updateName(request.getName(), username);
        return "Updated name successfully.";
    }

}
