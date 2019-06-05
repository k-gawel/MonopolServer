package org.california.monopolserver.service.game;

import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.model.ws_message.request.action.ChatMessageRequest;
import org.california.monopolserver.model.ws_message.response.game.ChatMessageResponse;
import org.california.monopolserver.service.CustomMessageTemplate;
import org.california.monopolserver.utils.annotations.RequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final CustomMessageTemplate messageTemplate;

    @Autowired
    public ChatService(CustomMessageTemplate messageTemplate) {
        this.messageTemplate = messageTemplate;
    }


    @RequestProcessor(ChatMessageRequest.class)
    public void message(ChatMessageRequest request) {
        Player player  = GameRegistry.getPlayerByUuid(request.player);
        String message = request.message;

        ChatMessageResponse chatMessageResponse = new ChatMessageResponse(player, message);

        messageTemplate.sendMessage(chatMessageResponse);
    }

}
