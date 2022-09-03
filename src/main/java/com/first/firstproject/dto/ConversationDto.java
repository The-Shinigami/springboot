package com.first.firstproject.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationDto {
    MessageDto message;
    UserDto sender;
    UserDto reciever;
}
