package com.canvasbids.bid.service;

import com.canvasbids.bid.exception.ChatDoesNotExistException;
import com.canvasbids.bid.dao.ChatDao;
import com.canvasbids.bid.dao.MessageDao;
import com.canvasbids.bid.dto.GetChatDTO;
import com.canvasbids.bid.entity.Chat;
import com.canvasbids.bid.entity.Message;
import jakarta.persistence.Embeddable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Embeddable
public class ChatService {
    @Autowired
    ChatDao chatDao;

    @Autowired
    MessageDao messageDao;


    public Date sendMessage(String sender, String receiver, String body) throws ChatDoesNotExistException {
        Message message = Message.builder()
                .messageBody(body)
                .senderId(sender)
                .receiverId(receiver)
                .createdAt(new Date(System.currentTimeMillis()))
                .build();
        String[] users = new String[]{sender, receiver};
        Arrays.sort(users);
        String chatId = users[0]+'$'+users[1];
        message.setChatId(chatId);
        Optional<Chat> chatOptional = chatDao.findById(chatId);

        if(chatOptional.isEmpty())throw new ChatDoesNotExistException();

        Message res = messageDao.save(message);

        return res.getCreatedAt();
    }

    public void updateMessage(String chatId, String sender, Date createdAt, String body)
    {
        messageDao.updateMessage(chatId, sender, createdAt, body);
    }

    public List<Message> getAllMessages(String chatId, int offset, int size){
        return messageDao.findAllByChatId(chatId, PageRequest.of(offset, size).withSort(Sort.Direction.DESC, "createdAt"));
    }

    public List<GetChatDTO> getAllChats(String userId, int offset, int size){
        List<Chat> chats = chatDao.findByUserId(userId, PageRequest.of(offset, size).withSort(Sort.Direction.DESC, "createdAt"));
        List<GetChatDTO> res = new ArrayList<>();

        for(Chat chat:chats){
            String name = chat.getUser1Name().equals(userId)? chat.getUser2Name() : chat.getUser1Name();
            String picture = chat.getUser1Name().equals(userId)? chat.getUser2Pic() : chat.getUser1Pic();
            res.add(GetChatDTO.builder()
                            .chatId(chat.getId())
                            .createdAt(chat.getCreatedAt())
                            .username(name)
                            .userPicture(picture)
                    .build());
        }

        return res;
    }

    public void createChat(Map<String, String> map){
        Chat chat = Chat.builder()
                .user1(map.get("user1"))
                .user2(map.get("user2"))
                .user1Name(map.get("user1Name"))
                .user2Name(map.get("user2Name"))
                .user1Pic(map.get("user1Pic"))
                .user2Pic(map.get("user2Pic"))
                .id(map.get("id"))
                .build();
        chatDao.save(chat);
    }
}
