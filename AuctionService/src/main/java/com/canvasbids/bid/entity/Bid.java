package com.canvasbids.bid.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@IdClass(BidId.class)
public class Bid {
    @Id
    String postId;
    @Id
    String userId;
    String name;
    String picture;
    long amount;
}
