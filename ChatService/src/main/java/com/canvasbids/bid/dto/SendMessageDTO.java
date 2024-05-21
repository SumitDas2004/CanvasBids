package com.canvasbids.bid.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class SendMessageDTO {
    @NotBlank
    String receiver;
    @NotBlank
    String messageBody;
}
