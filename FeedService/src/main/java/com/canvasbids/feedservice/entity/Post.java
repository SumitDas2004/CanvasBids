package com.canvasbids.feedservice.entity;

import com.canvasbids.feedservice.configurations.PostIdGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="post")
public class Post {
    @GenericGenerator(name="postIdGenerator", type = PostIdGenerator.class)
    @GeneratedValue(generator = "postIdGenerator")
    @Id
    private String id;
    private String description;
    private String userId;
    @ElementCollection
    private List<String> pictures;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    @JsonIgnore
    private Set<Like> likes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    @JsonIgnore
    private List<Comment> comments;
    @CreationTimestamp
    Date createdAt;
    @UpdateTimestamp
    Date updatedAt;
    private boolean auctionItemFlag;
}
