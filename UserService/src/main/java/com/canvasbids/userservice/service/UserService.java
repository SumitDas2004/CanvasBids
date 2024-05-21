package com.canvasbids.userservice.service;

import com.canvasbids.userservice.dao.ChatFeignDao;
import com.canvasbids.userservice.dao.ConnectionRequestDao;
import com.canvasbids.userservice.dao.UserDao;
import com.canvasbids.userservice.dto.*;
import com.canvasbids.userservice.entity.ConnectionRequest;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService {
    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ConnectionRequestDao connectionRequestDao;

    @Autowired
    UserDao userdao;

    @Autowired
    ChatFeignDao chatFeignDao;

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

    @Transactional
    public void acceptConnectionRequest(String id, String receiver){
        ConnectionRequest request = connectionRequestDao.findById(id).get();
        if(!request.getReceiverId().equals(receiver))throw new RuntimeException("User is not the recipient of the request");
        connectionRequestDao.delete(request);
        createChat(request);
    }

    public void createChat(ConnectionRequest request){
        Map<String, String> details = new HashMap<>();

        details.put("id", request.getId());
        details.put("user1", request.getSenderId());
        details.put("user2", request.getReceiverId());
        details.put("user1Pic", request.getSenderPic());
        details.put("user2Pic", request.getReceiverPic());
        details.put("user1Name", request.getSenderName());
        details.put("user2Name", request.getReceiverName());

        chatFeignDao.createChat(details);

    }

    public void sendConnectionRequest(String sender, String receiver){
        if(sender.equals(receiver))throw new RuntimeException("Can't send request to self.");

        Map<String, String> senderDetails = getNameAndPicture(sender);
        Map<String, String> receiverDetails = getNameAndPicture(receiver);

        ConnectionRequest request = ConnectionRequest.builder()
                .id(generateConnectionRequestId(sender, receiver))
                .senderId(sender)
                .receiverId(receiver)
                .senderName(senderDetails.get("name"))
                .receiverName(receiverDetails.get("name"))
                .senderPic(senderDetails.get("picture"))
                .receiverPic(receiverDetails.get("picture"))
                .build();

        connectionRequestDao.save(request);
    }

    public String generateConnectionRequestId(String sender, String receiver){
        String[] arr = new String[]{sender, receiver};
        Arrays.sort(arr);
        return arr[0]+"$"+arr[1];
    }

}
