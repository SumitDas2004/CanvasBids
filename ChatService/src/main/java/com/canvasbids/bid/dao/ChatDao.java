package com.canvasbids.bid.dao;

import com.canvasbids.bid.entity.Chat;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatDao extends JpaRepository<Chat, String> {
    @Query("SELECT c FROM Chat c WHERE c.user1=?1 OR c.user2=?1")
    List<Chat> findByUserId(String user, PageRequest pageRequest);
}
