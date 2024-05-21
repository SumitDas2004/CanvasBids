package com.canvasbids.bid.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@IdClass(BidId.class)
public class Bid {
    @Id
    private String postId;
    @Id
    private String userId;
    private String name;
    private String picture;
    private long amount;
    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
    private Boolean isWinner;
}
