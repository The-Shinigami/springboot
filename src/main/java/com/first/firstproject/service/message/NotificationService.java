package com.first.firstproject.service.message;

import com.first.firstproject.dto.ConversationDto;
import com.first.firstproject.dto.MessageDto;
import com.first.firstproject.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendGlobalNotification() {

        UserDto sender = new UserDto();
        UserDto reviever = new UserDto();
        MessageDto message = new MessageDto("Global message");
        ConversationDto conversationDto = new ConversationDto(message, sender, reviever);

        messagingTemplate.convertAndSend("/topic/global-notifications", conversationDto);
    }

    public void sendPrivateNotification(final String userId) {
        UserDto sender = new UserDto();
        UserDto reviever = new UserDto();
        MessageDto message = new MessageDto("Private message");
        ConversationDto conversationDto = new ConversationDto(message, sender, reviever);
        messagingTemplate.convertAndSendToUser(userId,"/topic/private-notifications", conversationDto);
    }
}
