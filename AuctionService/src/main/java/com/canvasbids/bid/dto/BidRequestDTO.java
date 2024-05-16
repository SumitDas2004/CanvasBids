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
    private String name;
    @NotBlank
    private String postId;
    @NotBlank
    private String picture;
    private long amount;

    public Bid toBid(){
        return Bid.builder()
                .name(this.name)
                .postId(this.postId)
                .picture(this.picture)
                .amount(this.amount)
                .build();
    }
}
