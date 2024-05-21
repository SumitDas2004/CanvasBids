package com.canvasbids.feedservice.dao;

import com.canvasbids.feedservice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface PostDao extends JpaRepository<Post, String> {
    @Query("SELECT p FROM Post p WHERE p.expiresAt<?1 AND p.isAuctionOpen=true")
    List<Post> getPostsExpiringBeforeGivenTime(Date currentTime);

    List<Post> findByUserId(String userId);


    @Modifying
    @Transactional
    @Query("UPDATE Post p SET p.expiresAt=?1 WHERE p.id=?2")
    void updateTimer(Date newTime, String postId);
}
