package com.canvasbids.bid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GetChatDTO {
    private String chatId;
    private Date createdAt;
    private String username;
    private String userPicture;
}
