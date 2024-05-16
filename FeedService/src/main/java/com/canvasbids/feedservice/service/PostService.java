package com.canvasbids.feedservice.service;

import com.canvasbids.feedservice.dao.LikeDao;
import com.canvasbids.feedservice.dao.PostDao;
import com.canvasbids.feedservice.dao.UserFeignDao;
import com.canvasbids.feedservice.dto.CreatePostDTO;
import com.canvasbids.feedservice.dto.GetPostDTO;
import com.canvasbids.feedservice.dto.UpdatePostDTO;
import com.canvasbids.feedservice.dto.UserDetailsDTO;
import com.canvasbids.feedservice.entity.Post;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class PostService {
    @Autowired
    PostDao postDao;

    @Autowired
    LikeDao likeDao;

    @Autowired
    UserFeignDao userFeignDao;

    public String createPost(CreatePostDTO request, String username){
        Post post= request.toPost();
        post.setUserId(username);
        postDao.save(post);
        return "Created post successfully.";
    }

    @Transactional
    public String deletePost(String postId) {
        postDao.deleteById(postId);
        return "Deleted post successfully.";
    }

    public String updatePost(UpdatePostDTO request) {
        Post post = postDao.findById(request.getPostId()).get();
        post = request.updatePost(post);
        postDao.save(post);
        return "Post updated successfully.";
    }

    public GetPostDTO toGetPostDTO(Post post){
        return GetPostDTO.builder()
                .id(post.getId())
                .cntLikes(post.getLikes().size())
                .cntComments(post.getComments().size())
                .auctionItemFlag(post.isAuctionItemFlag())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .pictures(post.getPictures())
                .description(post.getDescription())
                .userdetails(toUserDetailsDTO(post.getUserId()))
                .build();
    }

    public UserDetailsDTO toUserDetailsDTO(String username){
        Map<String, String> userdetails = userFeignDao.getNameAndPicture(username);
        return UserDetailsDTO.builder()
                .name(userdetails.get("name"))
                .picture(userdetails.get("picture"))
                .userId(username)
                .build();
    }

    public List<GetPostDTO> fetchPosts(int page, int size){
        Page<Post> posts = postDao.findAll(PageRequest.of(page, size).withSort(Sort.Direction.DESC,"createdAt"));
        return posts.stream().map(this::toGetPostDTO).collect(Collectors.toList());
    }
}
