package com.first.firstproject.service.user;

import com.first.firstproject.dto.OnlineUser;
import com.first.firstproject.dto.UserDto;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
@Service
public class OnlineUsersService {

    @Getter
    @Setter
    private static ArrayList<OnlineUser> onlineUsers = new ArrayList<OnlineUser>();



    public static SimpUserRegistry simpUserRegistry;

    OnlineUsersService(SimpUserRegistry simpUserRegistry){
        this.simpUserRegistry = simpUserRegistry;
    }

    private static int  index = 0;

    public static ArrayList<OnlineUser> setOnlineUser(OnlineUser onlineUser){
          onlineUsers.add(onlineUser);
        return onlineUsers;
    }

    public static ArrayList<OnlineUser> setUserToOnlineUser(UserDto userDto){

        OnlineUser newOnlineUser;

        if(onlineUsers.size() == 0) {
            newOnlineUser = new OnlineUser();
        }
        else {
            OnlineUsersService.resetOnlineUsers(userDto);
            newOnlineUser = onlineUsers.get(OnlineUsersService.index);
        }

        newOnlineUser.setUser(userDto);
        onlineUsers.set(OnlineUsersService.index,newOnlineUser);

        OnlineUsersService.index++;
      //  System.out.println("after add "+OnlineUsersService.getOnlineUsers().toString());
//        remove disconnected users
//        OnlineUsersService.resetOnlineUsers();


        return onlineUsers;
    }
    public static ArrayList<OnlineUser> setOnlineIdToOnlineUser(String onlineId){


       // System.out.println(OnlineUsersService.index);
        OnlineUser newOnlineUser;
        newOnlineUser = new OnlineUser();

        newOnlineUser.setOnlineId(onlineId);
        onlineUsers.add(newOnlineUser);

        return onlineUsers;
    }

    public static  void resetOnlineUsers(UserDto userDto){

//        List<String> onlineIdList = OnlineUsersService.simpUserRegistry
//                .getUsers()
//                .stream()
//                .map(SimpUser::getName)
//                .collect(Collectors.toList());

  //     System.out.println(onlineIdList);
//        System.out.println("before "+OnlineUsersService.getOnlineUsers().toString());
//     System.out.println(OnlineUsersService.onlineUsers.size());
        for(OnlineUser onlineUser: OnlineUsersService.onlineUsers){

//            if(onlineUser.getUser()!= null)
//            System.out.println(onlineUser.getUser().getName());
           if(onlineUser.getUser()!= null && onlineUser.getUser().getId().equals(userDto.getId()))
           {
               onlineUsers.remove(onlineUser);
               OnlineUsersService.index --;
//               System.out.println("after "+OnlineUsersService.getOnlineUsers().toString());
           }

        }

   //     System.out.println("after remove "+OnlineUsersService.getOnlineUsers().toString());


    }


    public static void removeEmptyOnlineUser(){

        List<OnlineUser> newOnlineUsers =  OnlineUsersService
                .onlineUsers.stream()
                .filter(onlineUser -> onlineUser.getUser()!=null)
                .collect(Collectors.toList());
        OnlineUsersService.setOnlineUsers(new ArrayList<OnlineUser>(newOnlineUsers));
    }
}
