package com.first.firstproject.config;

import com.first.firstproject.dto.OnlineUser;
import com.first.firstproject.dto.UserDto;
import com.first.firstproject.service.user.OnlineUsersService;
import com.first.firstproject.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpAsyncRequestControl;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RmeSessionChannelInterceptor implements ChannelInterceptor {

    @Autowired
    UserServiceImpl userServiceImpl;




    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {


        MessageHeaders headers = message.getHeaders();
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        MultiValueMap<String, String> multiValueMap = headers.get(StompHeaderAccessor.NATIVE_HEADERS,MultiValueMap.class);

         UserDto user = new UserDto();

         try {
             multiValueMap.getFirst("id");
             try {

                 if(userServiceImpl.isUser(multiValueMap.getFirst("id"))) {
                     for (Map.Entry<String, List<String>> head : multiValueMap.entrySet()) {

                         //   System.out.println(head.getKey() + "#" + head.getValue());
                         if (head.getKey().equals("id")) {
                             user.setId(head.getValue().get(0));
                         }
                         if (head.getKey().equals("name")) {
                             user.setName(head.getValue().get(0));
                         }


                     }
                     if(user.getId() != null && user.getName() != null ){
                         OnlineUsersService.setUserToOnlineUser(user);
                     }
                     // OnlineUsersService.resetOnlineUsers();
//         System.out.println(user.toString());
                     //     System.out.println(OnlineUsersService.getOnlineUsers().toString());
                 }

             }
             catch (Exception e)
             {
                 if(userServiceImpl.isUser(multiValueMap.getFirst("id"))) {
                     for (Map.Entry<String, List<String>> head : multiValueMap.entrySet()) {

                         //   System.out.println(head.getKey() + "#" + head.getValue());
                         if (head.getKey().equals("id")) {
                             user.setId(head.getValue().get(0));
                         }
                         if (head.getKey().equals("name")) {
                             user.setName(head.getValue().get(0));
                         }


                     }
                     if(user.getId() != null && user.getName() != null ){
                         OnlineUsersService.setUserToOnlineUser(user);
                     }
                     // OnlineUsersService.resetOnlineUsers();
//         System.out.println(user.toString());
                     //     System.out.println(OnlineUsersService.getOnlineUsers().toString());
                 }
             }
         }
         catch (Exception e)
         {
             System.out.println("key error");
             OnlineUsersService.removeEmptyOnlineUser();
         }

// try {
//     if(userServiceImpl.isUser(multiValueMap.getFirst("id"))) {
//         for (Map.Entry<String, List<String>> head : multiValueMap.entrySet()) {
//
//             //   System.out.println(head.getKey() + "#" + head.getValue());
//             if (head.getKey().equals("id")) {
//                 user.setId(head.getValue().get(0));
//             }
//             if (head.getKey().equals("name")) {
//                 user.setName(head.getValue().get(0));
//             }
//
//
//         }
//         if(user.getId() != null && user.getName() != null ){
//             OnlineUsersService.setUserToOnlineUser(user);
//         }
//        // OnlineUsersService.resetOnlineUsers();
////         System.out.println(user.toString());
//    //     System.out.println(OnlineUsersService.getOnlineUsers().toString());
//     }
// }
// catch (Exception e)
// {
//     System.out.println("error : " );
//OnlineUsersService.removeEmptyOnlineUser();
////     OnlineUsersService.resetOnlineUsers();
////     System.out.println(OnlineUsersService.getOnlineUsers().toString());
// }




        return message;
    }
}
