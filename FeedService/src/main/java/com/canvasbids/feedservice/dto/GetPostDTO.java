package com.canvasbids.feedservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPostDTO {
    private String id;
    private String description;
    private UserDetailsDTO userdetails;
    private List<String> pictures;
    private long cntLikes;
    private long cntComments;
    Date createdAt;
    Date updatedAt;
    private boolean auctionItemFlag;
    private long minimumBidAmount;
}
