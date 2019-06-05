package org.california.monopolserver.model.ws_message.response.player;

import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;
import org.california.monopolserver.utils.annotations.JSONSubTypes;
import org.california.monopolserver.model.ws_message.response.ResponseMessage;

@JSONTypeInfo("player_action_type")
@JSONSubTypes({
        @JSONSubTypes.Type(value = PlayerLeaveResponse.class, name = "leave"),
        @JSONSubTypes.Type(value = PlayerMoveResponse.class, name = "move")
})
public abstract class PlayerActionResponse extends ResponseMessage {

    public final String player_uuid;

    public PlayerActionResponse(Player player) {
        super(player.getGame());

        this.player_uuid = player.getUUID();
    }

}
