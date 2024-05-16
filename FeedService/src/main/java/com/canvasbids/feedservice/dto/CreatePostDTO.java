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
public class CreatePostDTO {
    @NotBlank
    private String description;
    @NotNull
    private List<String> pictures;
    private boolean isAuctionItem;
    public Post toPost(){
        return Post.builder()
                .description(this.description)
                .pictures(this.pictures)
                .auctionItemFlag(this.isAuctionItem)
                .cntComments(0)
                .cntLikes(0)
                .build();
    }
}
