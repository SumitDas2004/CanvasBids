package com.canvasbids.feedservice.dao;

import com.canvasbids.feedservice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDao extends JpaRepository<Post, String> {
}
