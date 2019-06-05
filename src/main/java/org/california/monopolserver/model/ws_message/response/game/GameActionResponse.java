package org.california.monopolserver.model.ws_message.response.game;

import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;
import org.california.monopolserver.utils.annotations.JSONSubTypes;
import org.california.monopolserver.model.ws_message.response.ResponseMessage;

@JSONTypeInfo("game_action_type")
@JSONSubTypes({
        @JSONSubTypes.Type(value = NewPlayerResponse.class, name = "new_player"),
        @JSONSubTypes.Type(value = NewTourResponse.class, name = "new_tour"),
        @JSONSubTypes.Type(value = ChatMessageResponse.class, name = "chat")
})
public abstract class GameActionResponse extends ResponseMessage {

    public GameActionResponse(Game game) {
        super(game);
    }

}
