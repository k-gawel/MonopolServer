package org.california.monopolserver.model.ws_message.response;

import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;
import org.california.monopolserver.utils.annotations.JSONSubTypes;
import org.california.monopolserver.model.ws_message.response.game.GameActionResponse;
import org.california.monopolserver.model.ws_message.response.player.PlayerActionResponse;
import org.california.monopolserver.model.ws_message.response.transaction.TransactionResponse;

@JSONTypeInfo(value = "response_type")
@JSONSubTypes({
        @JSONSubTypes.Type(value = GameActionResponse.class, name = "game_action"),
        @JSONSubTypes.Type(value = TransactionResponse.class, name = "transaction"),
        @JSONSubTypes.Type(value = PlayerActionResponse.class, name = "player_action")
})
public abstract class ResponseMessage {
    public String game;

    public ResponseMessage(Game game) {
        this.game = game.getUUID();
    }
}
