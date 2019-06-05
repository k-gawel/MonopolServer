package org.california.monopolserver.model.interfaces;

import org.california.monopolserver.instance.board.board.Board;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.utils.GameInstance;

public interface Landable extends GameInstance {

    default boolean standsOn(Player player) {
        Board board = player.getGame().board;
        return board.get(this).players.contains(player);
    }

}
