package com.canvasbids.bid.dao;

import com.canvasbids.bid.entity.Message;
import com.canvasbids.bid.entity.MessageId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface MessageDao extends JpaRepository<Message, MessageId> {
    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.messageBody=?4 WHERE m.senderId=?2 AND m.chatId=?1 AND m.createdAt=?3")
    void updateMessage(String chatId, String sender, Date createdAt, String body);

    @Query("SELECT m FROM Message m WHERE m.chatId=?1")
    List<Message> findAllByChatId(String chatId, PageRequest pageRequest);
}
