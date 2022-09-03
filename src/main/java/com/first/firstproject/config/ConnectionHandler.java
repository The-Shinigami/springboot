package com.first.firstproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Configuration
public class ConnectionHandler extends TextWebSocketHandler {

    //private final static Logger logger = LoggerFactory.getLogger(YourHandler .class);
    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
//        logger.debug("Connected : " + session);
        System.out.println("Connected : " + session.getPrincipal().getName());
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("Disconnected : " + session.getPrincipal().getName());
        sessions.remove(session);
    }


}
