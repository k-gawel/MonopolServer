package org.california.monopolserver.model.ws_message.response.player;

import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;

@JSONTypeInfo
public class PlayerLeaveResponse extends PlayerActionResponse {

    public PlayerLeaveResponse(Player player) {
        super(player);
    }

}
