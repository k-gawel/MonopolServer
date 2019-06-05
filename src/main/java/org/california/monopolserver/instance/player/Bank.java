package org.california.monopolserver.instance.player;

import org.california.monopolserver.instance.utils.TransferableCollection;
import org.california.monopolserver.instance.game.Game;

public class Bank extends Player {

    public Bank(Game game) {
        super(game);
        if(game.bank != null)
            throw new IllegalStateException("Bank already exists in game " + game.getUUID());

        this.name = "BANK";
        this.properties = new TransferableCollection(this);
    }

}
