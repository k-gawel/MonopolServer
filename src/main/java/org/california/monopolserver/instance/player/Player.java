package org.california.monopolserver.instance.player;

import org.california.monopolserver.instance.board.field.Field;
import org.california.monopolserver.instance.game.ColorPicker;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.instance.utils.AbstractGameInstance;
import org.california.monopolserver.instance.utils.TransferableCollection;

public class Player extends AbstractGameInstance implements Comparable<Player> {

    public final String session = java.util.UUID.randomUUID().toString();
    public final String name;
    public final String color;
    public final TransferableCollection properties;

    public Player(Game game, String name) {
        super(game);
        this.name = name;
        this.color = ColorPicker.getNextColor(game);
        this.properties = new TransferableCollection(this);
    }

    protected Player(Game game, String name, String color) {
        this.game = game;
        this.name = name;
        this.color = color;
        this.properties = new TransferableCollection(this);
    }

    private String session() {
        return new StringBuilder()
                .append(game.getUUID()).append("|")
                .append(this.getUUID()).append("|")
                .append(java.util.UUID.randomUUID().toString())
                .toString();
    }


    public Field field() {
        return getBoard().get(this);
    }


    public boolean isBank() {
        return this instanceof Bank;
    }


    @Override
    public String toString() {
        return "[ " + name + ", " + color + ", " + getUUID() + " ]";
    }

    @Override
    public int compareTo(Player o) {
        return properties.compareTo(o.properties);
    }
}
