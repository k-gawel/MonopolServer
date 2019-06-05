package org.california.monopolserver.model.ws_message.response.game;

import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;

@JSONTypeInfo
public class ChatMessageResponse extends GameActionResponse {

    public String player;
    public String message;

    public ChatMessageResponse(Player player, String message) {
        super(player.getGame());

        this.player = player.getUUID();
        this.message = message;
    }

}
