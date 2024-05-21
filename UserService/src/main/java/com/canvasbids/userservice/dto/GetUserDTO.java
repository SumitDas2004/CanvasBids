package com.canvasbids.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetUserDTO {
    private String name;
    private String email;
    private Date createdOn;
    private Date updatedOn;
    private String picture;
}
