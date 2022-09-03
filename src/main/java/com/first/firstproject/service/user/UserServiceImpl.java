package com.first.firstproject.service.user;

import com.first.firstproject.dto.UserDto;
import com.first.firstproject.entity.User;
import com.first.firstproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserServiceImpl  implements UserService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }


    @Override
    public boolean save(UserDto userDto) {
       User user = new User(userDto.getId(),userDto.getName(), userDto.getUsername(), userDto.getPassword());
       return userRepository.save(user) != null;
    }

    @Override
    public boolean isUser(String id) {
        Optional<User> user = userRepository.findById(id);
//        System.out.println("user with id :"+ id+" is found : "+ user.isPresent());
        return user.isPresent();
    }

    @Override
    public String getNewId(){
        List<User> users = userRepository.findAll();
        Integer id = Integer.parseInt(users.get(users.size()-1).getId())+1;
       return  id.toString();
    }

    @Override
    public User getByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
      if(user.isPresent())
          return  user.get();
      return new User("","","","");
    }

}
