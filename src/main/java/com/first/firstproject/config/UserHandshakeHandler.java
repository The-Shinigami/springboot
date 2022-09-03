package com.first.firstproject.config;


import com.first.firstproject.service.user.OnlineUsersService;
import com.sun.security.auth.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;

  public  class UserHandshakeHandler extends DefaultHandshakeHandler {
    private final Logger LOG = (Logger) LoggerFactory.getLogger(UserHandshakeHandler.class);






    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        final String randomId = UUID.randomUUID().toString();
//        LOG.info("User with ID '{}' opened the page", randomId);
//        if(!OnlineUsersService.getOnlineUsers().isEmpty())
//            OnlineUsersService.getOnlineUsers().get(0).getId()
    //   System.out.println("User with ID "+ randomId);

        OnlineUsersService.setOnlineIdToOnlineUser(randomId);
        return new UserPrincipal(randomId) ;
    }
}
