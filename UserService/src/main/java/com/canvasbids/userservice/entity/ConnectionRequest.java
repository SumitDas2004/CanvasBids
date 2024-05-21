package com.canvasbids.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConnectionRequest {
    @Id
    private String id;
    private String senderId;
    private String receiverId;
    private String senderPic;
    private String receiverPic;
    private String senderName;
    private String receiverName;
    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;
}
