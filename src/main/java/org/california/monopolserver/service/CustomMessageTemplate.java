package org.california.monopolserver.service;

import org.california.monopolserver.model.ws_message.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomMessageTemplate  {


    private final SimpMessagingTemplate template;


    @Autowired
    public CustomMessageTemplate(SimpMessagingTemplate template) {
        this.template = template;
    }


    public void sendMessage(ResponseMessage message) {
        if(message == null) return;

        String dest = "/game/".concat(message.game);
        template.convertAndSend(dest, message);
    }




}
