package com.canvasbids.feedservice.dto;

import com.canvasbids.feedservice.entity.Comment;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDTO {
    @NotBlank
    String body;
    @NotBlank
    String postId;
    public Comment toComment(){
        return Comment.builder()
                .body(body)
                .build();
    }

}
