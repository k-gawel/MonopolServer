package org.california.monopolserver.instance.transferable.money;

import org.california.monopolserver.instance.utils.AbstractGameInstance;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.game.Game;
import org.california.monopolserver.instance.utils.GameInstance;
import org.california.monopolserver.model.interfaces.Transferable;

public class Money extends AbstractGameInstance implements Transferable {

    private int amount;
    private Player owner;

    protected Money() {}

    public Money(Game game, int amount) {
        super(game);
        this.amount = amount;
    }

    public Money(GameInstance gameInstance) {
        super(gameInstance.getGame());
        this.amount = 0;
    }

    public Money(Game game) {
        super(game);
        this.amount = 0;
    }

    public Money(Player owner, int amount) {
        super(owner.getGame());
        this.owner = owner;
        this.amount = amount;
    }


    public int intValue() {
        return this.amount;
    }


    public int add(int amount) {
        this.amount += amount;
        return this.amount;
    }


    public Money add(Money money) {
        this.amount += money.amount;
        return this;
    }


    public int subtract(int amount) {
        if(this.amount < amount)
            throw new IllegalStateException("There is not enough money");

        this.amount -= amount;
        return this.amount;
    }


    public Money subtract(Money money) {
        if(this.compareTo(money) < 0)
            throw new IllegalStateException("There is not enough money");

        this.amount -= money.amount;
        return this;
    }


    public Money getPart(int divider) {
        int part = amount / divider;
        return new Money(game, part);
    }

    @Override
    public Player getOwner() {
        return this.owner;
    }


    @Override
    public Money getBasicPrice() {
        return new Money(game, this.amount);
    }


    @Override
    public void transfer(Player sender, Player receiver) throws IllegalStateException {
        sender.properties.remove(this);
        receiver.properties.add(this);
    }

    @Override
    public String toString() {
        return "[ $" + amount + ", " + game.getUUID() + " ]";
}
}
