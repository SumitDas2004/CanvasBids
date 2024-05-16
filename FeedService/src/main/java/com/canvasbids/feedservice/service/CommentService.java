package com.canvasbids.feedservice.service;

import com.canvasbids.feedservice.dao.CommentDao;
import com.canvasbids.feedservice.dao.PostDao;
import com.canvasbids.feedservice.dao.UserFeignDao;
import com.canvasbids.feedservice.dto.CommentDTO;
import com.canvasbids.feedservice.dto.UpdateCommentDTO;
import com.canvasbids.feedservice.entity.Comment;
import com.canvasbids.feedservice.entity.Post;
import com.canvasbids.feedservice.exception.PostDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    PostDao postDao;

    @Autowired
    CommentDao commentDao;

    @Autowired
    UserFeignDao userFeignDao;

    public String comment(CommentDTO request, String username) throws PostDoesNotExistException {
        Optional<Post> postOptional = postDao.findById(request.getPostId());
        if(!postOptional.isPresent())throw new PostDoesNotExistException();
        Post post = postOptional.get();
        Comment comment = getCommentFromRequest(request, username);
        post.getComments().add(comment);
        comment.setPost(post);
        postDao.save(post);
        return "Successfully added comment on the post.";
    }

    public String updateComment(UpdateCommentDTO request) throws PostDoesNotExistException {
        commentDao.findByIdAndUpdate(request.getCommentId(), request.getBody());
        return "Updated comment successfully.";
    }

    public String deleteComment(long id) throws PostDoesNotExistException {
        commentDao.deleteById(id);
        return "Deleted comment successfully.";
    }


    public List<Comment> findCommentsByPost(String postId, int offset, int size){
        return commentDao.findByPostId(postId, PageRequest.of(offset, size));
    }

    private Comment getCommentFromRequest(CommentDTO request, String username){
        Comment comment = request.toComment();
        Map<String, String> map = userFeignDao.getNameAndPicture(username);
        String name = map.get("name");
        String picture = map.get("picture");
        comment.setName(name);
        comment.setPicture(picture);
        comment.setUserId(username);
        return comment;
    }

}
