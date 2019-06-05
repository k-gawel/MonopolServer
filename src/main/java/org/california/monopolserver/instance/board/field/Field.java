package org.california.monopolserver.instance.board.field;

import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.utils.GameInstance;
import org.california.monopolserver.instance.utils.Instance;
import org.california.monopolserver.model.interfaces.Landable;

import java.util.Collection;
import java.util.HashSet;

public abstract class Field extends Instance implements GameInstance {

    public Collection<Player> players = new HashSet<>();
    public Game game;
    public int number;

    Field(Game game, int number) {
        if(number < 0)
            throw new IllegalStateException("Field number can not be lower than zero.");
        this.game = game;
        this.number = number;
    }


    public abstract Landable getLandable();

    public boolean removePlayer(Player player) {
        return players.remove(player);
    }

    public int getNumber() {
        return this.number;
    }

    public Collection<Player> getPlayers() {
        return players;
    }

    @Override
    public Game getGame() {
        return game;
    }

}
