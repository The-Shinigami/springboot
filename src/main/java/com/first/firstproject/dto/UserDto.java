package com.first.firstproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserDto {
    private String id;
    @NotBlank(message = "Name is Required")
    private String name;
    @NotBlank(message = "Username is Required")
    private  String username;
    @NotBlank(message = "Password is Required")
    private String password;
}
