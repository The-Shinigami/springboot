package com.first.firstproject.service.user;



import com.first.firstproject.dto.UserDto;
import com.first.firstproject.entity.User;


import java.util.List;

public interface UserService {
    List<User> getAll();
    boolean save(UserDto user);
    boolean isUser(String id);
    String getNewId();
    User getByUsername(String username);
}
