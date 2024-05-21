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
    @NotBlank
    private String postId;
    private boolean auctionItemFlag;
    private long minimumBidAmount;
    public Post updatePost(Post post){
        post.setDescription(this.description);
        post.setAuctionItemFlag(this.auctionItemFlag);
        post.setMinimumBidAmount(this.minimumBidAmount);
        return post;

    }
}
