package com.canvasbids.feedservice.service;

import com.canvasbids.feedservice.dao.LikeDao;
import com.canvasbids.feedservice.dao.PostDao;
import com.canvasbids.feedservice.dao.UserFeignDao;
import com.canvasbids.feedservice.dto.CreatePostDTO;
import com.canvasbids.feedservice.dto.GetPostDTO;
import com.canvasbids.feedservice.dto.UpdatePostDTO;
import com.canvasbids.feedservice.dto.UserDetailsDTO;
import com.canvasbids.feedservice.entity.Post;
import com.canvasbids.feedservice.exception.PostDoesNotExistException;
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

    public List<GetPostDTO> getPostsByUser(String userId){
        List<Post> posts = postDao.findByUserId(userId);
        return posts.stream().map(this::toGetPostDTO).toList();
    }

    //Used by bid service after an user bids on the post, simply the bid closing time is set to current time+10minutes
    public void updatePostTimer(String postId){
        postDao.updateTimer(new Date(System.currentTimeMillis()+1000*60*10), postId);
    }

    //Method used by closeAuctionScheduler to close all the auctions that have expired in the last minute
    public void closeAuction(){
        List<Post> posts = postDao.getPostsExpiringBeforeGivenTime(new Date(System.currentTimeMillis()));
        posts.stream().forEach(post->post.setIsAuctionOpen(false));
        postDao.saveAll(posts);
    }

    public GetPostDTO toGetPostDTO(Post post){
        return GetPostDTO.builder()
                .minimumBidAmount(post.getMinimumBidAmount())
                .id(post.getId())
                .cntLikes(post.getLikes().size())
                .cntComments(post.getComments().size())
                .auctionItemFlag(post.getAuctionItemFlag())
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

    //Used by BidService to check if the bid by the user is valid or not, i.e. the there is an active auction in the post and the bid amount is more than the minimum acceptable bid amount or not.
    public boolean isValidBid(String postId, long amount) throws PostDoesNotExistException {
        Optional<Post> optionalPost = postDao.findById(postId);
        if(optionalPost.isEmpty())throw new PostDoesNotExistException();
        Post post = optionalPost.get();
        return post.getIsAuctionOpen() && post.getAuctionItemFlag() && post.getMinimumBidAmount()<amount;
    }


}
