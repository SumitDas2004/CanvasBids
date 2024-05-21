package com.canvasbids.bid.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Chat {
    @Id
    private String id;
    @OneToMany
    private List<Message> messages;
    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;
    private String user1;
    private String user2;
    private String user1Pic;
    private String user2Pic;
    private String user1Name;
    private String user2Name;
}
