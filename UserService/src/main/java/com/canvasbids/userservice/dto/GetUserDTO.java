package com.canvasbids.userservice.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetUserDTO {
    private String name;
    private String email;
    private Date createdOn;
    private Date updatedOn;
    private String picture;
}
