package org.california.monopolserver.instance.player;

import org.california.monopolserver.instance.game.ColorPicker;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.instance.utils.TransferableCollection;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.instance.utils.AbstractGameInstance;

public class Player extends AbstractGameInstance {

    public final String session = java.util.UUID.randomUUID().toString();
    public String name;
    public String color;
    public TransferableCollection properties;

    public Player(Game game, String name) {
        super(game);

        this.game.addPlayer(this);
        this.name = name;
        this.color = ColorPicker.getNextColor(game);
        this.properties = new TransferableCollection(this);
        Money startMoney = new Money(game, game.startMoney);
        this.properties.add(startMoney);
    }

    protected Player(Game game) {
        super(game);
    }


    private String createSession() {
        return new StringBuilder()
                .append(game.getUUID()).append("|")
                .append(this.getUUID()).append("|")
                .append(java.util.UUID.randomUUID().toString())
                .toString();
    }


    public String getName() {
        return name;
    }


    public String getColor() {
        return color;
    }


    public boolean isBank() {
        return this instanceof Bank;
    }


    @Override
    public String toString() {
        return "[ " + name + ", " + color + ", " + getUUID() + " ]";
    }

}
