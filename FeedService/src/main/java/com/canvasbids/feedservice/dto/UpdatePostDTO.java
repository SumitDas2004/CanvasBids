package com.canvasbids.feedservice.dto;

import com.canvasbids.feedservice.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostDTO {
    @NotBlank
    private String description;
    @NotNull
    private List<String> pictures;
    @NotBlank
    private String postId;
    private boolean auctionItemFlag;
    public Post updatePost(Post post){
        post.setPictures(this.pictures);
        post.setDescription(this.description);
        post.setAuctionItemFlag(this.auctionItemFlag);
        return post;
    }
}
