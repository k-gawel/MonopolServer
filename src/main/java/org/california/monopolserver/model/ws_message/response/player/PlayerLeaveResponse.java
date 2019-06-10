package org.california.monopolserver.model.ws_message.response.player;

import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.utils.annotations.JSONTypeInfo;

@JSONTypeInfo
public class PlayerLeaveResponse extends PlayerActionResponse {

    public final String new_admin;
    public final String winner;
    public final String loser;
    public final boolean game_aborted;

    //XDDD

    public PlayerLeaveResponse(Player player, Player winner, Player loser, boolean aborted) {
        super(player);
        this.new_admin = player.getGame().admin.getUUID();
        this.winner = getUuid(winner);
        this.loser  = getUuid(loser);
        this.game_aborted = aborted;
    }

    private String getUuid(Player player) {
        return player != null ? player.getUUID() : null;
    }

}
