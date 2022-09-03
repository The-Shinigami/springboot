package com.first.firstproject.service.message;


import com.first.firstproject.dto.ConversationDto;
import com.first.firstproject.dto.MessageDto;
import com.first.firstproject.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WSService {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;

    @Autowired
    public WSService(SimpMessagingTemplate messagingTemplate, NotificationService notificationService) {
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
    }

//    public void notifyFrontend(final String message) {
//        ResponseMessage response = new ResponseMessage(message);
//        notificationService.sendGlobalNotification();
//
//        messagingTemplate.convertAndSend("/topic/messages", response);
//    }

    public void notifyUser(final String id, final ConversationDto conversationDto) {
        ConversationDto conversation = new ConversationDto(conversationDto.getMessage(), conversationDto.getSender(), conversationDto.getReciever());

        notificationService.sendPrivateNotification(id);
        System.out.println(id);
        messagingTemplate.convertAndSendToUser(id, "/topic/private-messages", conversation);
    }
}