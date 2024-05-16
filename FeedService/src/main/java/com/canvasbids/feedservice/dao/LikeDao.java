package com.canvasbids.feedservice.dao;

import com.canvasbids.feedservice.entity.Like;
import com.canvasbids.feedservice.entity.LikeId;
import com.canvasbids.feedservice.entity.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LikeDao extends JpaRepository<Like, LikeId> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM likes WHERE post_id=?1 AND user_id=?2", nativeQuery = true)
    void deleteByPostAndUser(String postId, String userId);

    @Query(value = "SELECT l FROM Like l WHERE l.post=?1")
    List<Like> findLikesByPost(Post post, PageRequest page);
}
