package com.canvasbids.feedservice.dto;

import com.canvasbids.feedservice.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDTO {
    @NotBlank
    private String description;
    @NotNull
    private List<String> pictures;
    private Boolean isAuctionItem;
    private long minimumBidAmount;
    public Post toPost(){
        System.out.println(this.isAuctionItem);
        return Post.builder()
                .description(this.description)
                .pictures(this.pictures)
                .auctionItemFlag(this.isAuctionItem)
                .minimumBidAmount(this.minimumBidAmount)
                .expiresAt(new Date(System.currentTimeMillis()+1000*60*60*24))
                .isAuctionOpen(this.isAuctionItem)
                .build();
    }
}
