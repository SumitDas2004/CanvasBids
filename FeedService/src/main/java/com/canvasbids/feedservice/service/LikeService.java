package com.canvasbids.feedservice.service;

import com.canvasbids.feedservice.dao.LikeDao;
import com.canvasbids.feedservice.dao.PostDao;
import com.canvasbids.feedservice.dao.UserFeignDao;
import com.canvasbids.feedservice.dto.LikeDTO;
import com.canvasbids.feedservice.entity.Like;
import com.canvasbids.feedservice.entity.Post;
import com.canvasbids.feedservice.exception.PostDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class LikeService {

    @Autowired
    UserFeignDao userFeignDao;

    @Autowired
    PostDao postDao;

    @Autowired
    LikeDao likeDao;


    @Transactional
    public String like(LikeDTO request, String username) throws PostDoesNotExistException {
        Optional<Post> postOptional = postDao.findById(request.getPostId());
        if(!postOptional.isPresent())throw new PostDoesNotExistException();
        Post post = postOptional.get();
        Like like = getLikeFromRequest(username, request);
        Set<Like> likes = post.getLikes();
        if(likes.contains(like)){
            likeDao.deleteByPostAndUser(request.getPostId(), username);
            post.getLikes().remove(like);
            postDao.save(post);
            return "Successfully removed like from the post.";
        }
        likes.add(like);
        like.setPost(post);
        postDao.save(post);
        return "Successfully liked the post.";
    }

    public List<Like> getLikes(String postId, int offset, int size){
        Post post = postDao.findById(postId).get();
        return likeDao.findLikesByPost(post, PageRequest.of(offset, size));
    }

    private Like getLikeFromRequest(String username, LikeDTO request){
        Like like = request.toLike();
        Map<String, String> map = getUserDetails(username);
        like.setPicture(map.get("picture"));
        like.setName(map.get("name"));
        like.setUserId(username);
        return like;
    }

    private Map<String, String> getUserDetails(String username){
        return userFeignDao.getNameAndPicture(username);
    }
}
