package org.california.monopolserver.model.ws_message.response.player;

import org.california.monopolserver.instance.executable.move.Move;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;

@JSONTypeInfo
public class PlayerMoveResponse extends PlayerActionResponse {

    public final String destination;

    public PlayerMoveResponse(Move move) {
        super(move.player);

        this.destination = move.destination.getUUID();
    }

}
