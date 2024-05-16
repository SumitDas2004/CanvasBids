package com.canvasbids.feedservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @ManyToOne
    @JoinColumn(name="post_id")
    @JsonIgnore
    private Post post;

    private String body;

    private String userId;

    private String name;

    private String picture;
    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date UpdatedAt;

}
