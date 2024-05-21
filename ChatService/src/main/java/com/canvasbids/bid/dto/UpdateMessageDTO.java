package com.canvasbids.bid.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMessageDTO {
    @NotBlank
    String chatId;
    @NotBlank
    String messageBody;
    @NotNull
    Date createdAt;
}
