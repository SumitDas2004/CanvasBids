package com.canvasbids.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO
{
    @NotBlank(message="Email can't be empty")
    String email;
    @NotBlank(message="Password can't be empty.")
    String password;
}
