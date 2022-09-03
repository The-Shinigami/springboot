package com.first.firstproject.controller;


import com.first.firstproject.dto.ConversationDto;
import com.first.firstproject.dto.MessageDto;
import com.first.firstproject.dto.OnlineUser;
import com.first.firstproject.dto.UserDto;
import com.first.firstproject.exception.response.Response;
import com.first.firstproject.service.message.NotificationService;
import com.first.firstproject.service.message.WSService;
import com.first.firstproject.service.user.OnlineUsersService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/")
@CrossOrigin("*")
public class webSocketMethodsController {
    @Autowired
    SimpMessagingTemplate template;
//
//    @PostMapping("/message")
//    public ResponseEntity<Response> sendMessage(@RequestBody MessageDto MessageDTO) {
//        template.convertAndSend("/topic/message", MessageDTO);
//        //return new ResponseEntity<>(HttpStatus.OK);
//        return new ResponseEntity<Response>(Response.ok().setPayload(MessageDTO), HttpStatus.OK);
//    }

//    @MessageMapping("/sendMessage")
//    public String receiveMessage(@Payload MessageDto MessageDTO) {
//        // receive message from client
//        return "hello";
//    }


//    @SendTo("/topic/message")
//    public ResponseEntity<Response> broadcastMessage(@Payload MessageDto MessageDTO,UserDto userDto) {
//        return new ResponseEntity<Response>(Response.ok().setPayload(MessageDTO), HttpStatus.OK);
//    }
//
//    @PostMapping("/conversation")
//    public ResponseEntity<Response> sendMessage(@RequestBody ConversationDto conversationDto) {
//
//        template.convertAndSend("/topic/conversation", conversationDto);
//        //return new ResponseEntity<>(HttpStatus.OK);
//      return new ResponseEntity<Response>(Response.ok().setPayload(conversationDto), HttpStatus.OK);
//    }
//
//    @MessageMapping({"/sendMessage"})
//    @SendTo("/topic/conversation")
//    public ResponseEntity<Response> broadcastMessage(@Payload ConversationDto conversationDto) {
//
//        return new ResponseEntity<Response>(Response.ok().setPayload(conversationDto), HttpStatus.OK);
//    }

//    -----------------------------------------------------------------------------------------------
    @Autowired
     private NotificationService notificationService;

    @Autowired
    private WSService service;

    @Autowired
    private SimpUserRegistry simpUserRegistry;

//    @MessageMapping("/private-message")
//    @SendToUser("/topic/private-messages")
//    public ResponseEntity<Response> getPrivateMessage(final ConversationDto conversationDto,
//                                             final Principal principal) throws InterruptedException {
//        Thread.sleep(1000);
//        notificationService.sendPrivateNotification(principal.getName());
//        return new ResponseEntity<Response>(Response.ok().setPayload(conversationDto), HttpStatus.OK);
//
//    }

    @PostMapping("/conversation/{id}")
    public ResponseEntity<Response> sendPrivateMessage(@PathVariable final String id,
                                   @RequestBody final ConversationDto conversationDto) {

//        System.out.println(this.simpUserRegistry
//                .getUsers()
//                .stream()
//                .map(SimpUser::getName)
//                .collect(Collectors.toList()));
       service.notifyUser(id,conversationDto);

        return new ResponseEntity<Response>(Response.ok().setPayload(conversationDto), HttpStatus.OK);
    }


    @GetMapping("/onlineusers")
    public ResponseEntity<Response> getOnlineUsers(){


         List<String> onlineUsers = this.simpUserRegistry
                 .getUsers()
                 .stream()
                 .map(SimpUser::getName)
                 .collect(Collectors.toList());

        List<OnlineUser> onlineUsersSelected = OnlineUsersService
                .getOnlineUsers()
                .stream()
                .filter(onlineUser -> onlineUsers.contains(onlineUser.getOnlineId()) && onlineUser.getUser() != null)
                .collect(Collectors.toList());

//        OnlineUsersService.setOnlineUsers(new ArrayList(onlineUsersSelected));

       template.convertAndSend("/topic/online-users", onlineUsersSelected);

        return new ResponseEntity<Response>(Response.ok().setPayload(OnlineUsersService
                .getOnlineUsers()), HttpStatus.OK);
    }
}
