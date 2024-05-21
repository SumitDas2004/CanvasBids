package com.canvasbids.feedservice.dto;

import com.canvasbids.feedservice.entity.Like;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeDTO {
    @NotBlank
    String postId;

    public Like toLike(){
        return Like.builder()
                .build();
    }
}
