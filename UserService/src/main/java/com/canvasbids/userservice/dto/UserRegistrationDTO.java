package com.canvasbids.userservice.dto;

import com.canvasbids.userservice.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationDTO {
    @NotBlank(message="Name can't be empty")
    String name;
    @NotBlank(message="Email can't be empty")
    String email;
    @NotBlank(message="Password can't be empty.")
    String password;
    @NotBlank(message="Profile picture can't be empty.")
    String picture;
    public User toUser(){
        return User.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .picture(this.picture)
                .connectionRequests(new HashSet<>())
                .build();
    }
}
