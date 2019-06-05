package org.california.monopolserver.model.ws_message.response.game;

import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.model.dto.game.PlayerDto;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;

@JSONTypeInfo
public class NewPlayerResponse extends GameActionResponse {

    public final PlayerDto player;

    public NewPlayerResponse(Player player) {
        super(player.getGame());

        this.player = new PlayerDto(player);
    }

}
