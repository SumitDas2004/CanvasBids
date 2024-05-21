package com.canvasbids.bid.dto;

import com.canvasbids.bid.entity.Bid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BidRequestDTO {
    @NotBlank
    private String postId;
    private long amount;

    public Bid toBid(){
        return Bid.builder()
                .postId(this.postId)
                .amount(this.amount)
                .isWinner(false)
                .build();
    }
}
