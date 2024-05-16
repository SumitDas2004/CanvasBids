package com.canvasbids.feedservice.dao;

import com.canvasbids.feedservice.entity.Comment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentDao extends JpaRepository<Comment, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Comment c SET c.body=?2 WHERE c.id=?1")
    void findByIdAndUpdate(long id, String body);

    List<Comment> findByPostId(String postId, PageRequest pageRequest);
}
