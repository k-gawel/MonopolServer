package org.california.monopolserver.model.dto.game;

import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.instance.player.Player;

import java.util.Collection;
import java.util.stream.Collectors;

public class GameLink {

    public String uuid;
    public int index;
    public Collection<String> players;

    public GameLink(Game g) {
        this.uuid    = g.getUUID();
        this.index   = g.number;
        this.players = g.players.stream().map(Player::getName).collect(Collectors.toSet());;
    }

}
