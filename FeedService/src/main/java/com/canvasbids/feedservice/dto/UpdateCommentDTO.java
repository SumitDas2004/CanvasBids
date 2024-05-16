package com.canvasbids.feedservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCommentDTO {
    @NotBlank
    Long commentId;
    @NotBlank
    String body;
}
