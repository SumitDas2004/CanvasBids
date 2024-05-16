package com.canvasbids.feedservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name="likes")
@IdClass(LikeId.class)
public class Like {

    @Id
    @ManyToOne
    @JoinColumn(name="post_id")
    @JsonIgnore
    private Post post;
    @Id
    private String userId;
    private String name;
    private String picture;

    @Override
    public boolean equals(Object o){
        Like like = (Like)o;
        return this.userId.equals(like.getUserId());
    }

    @Override
    public int hashCode(){
        return this.userId.hashCode();
    }
}
