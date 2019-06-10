package org.california.monopolserver.instance.player;

import org.california.monopolserver.instance.game.Game;

public class Bank extends Player {

    public Bank(Game game) {
        super(game, "BANK", "BLACK");
        if(game.bank != null)
            throw new IllegalStateException("Bank already exists in game " + game.getUUID());
    }

}
