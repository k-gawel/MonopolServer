package org.california.monopolserver.instance.transferable.town;

import org.california.monopolserver.instance.utils.AbstractGameInstance;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.instance.player.Bank;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.model.interfaces.Groupable;
import org.california.monopolserver.model.interfaces.Transferable;

public class Improvement extends AbstractGameInstance implements Transferable, Groupable {

    protected Player owner;
    private Town town;

    public Improvement(Town town) {
        super(town.getGame());
        this.town = town;

        if(town.getComponents().size() >= 5)
            throw new IllegalStateException("Town can not has more than 5 improvements");
        if(town.getGroup().getOwner() == null)
            throw new IllegalStateException("Can't create improvement if town region has no owner");

        this.owner = game.bank;
        this.owner.properties.add(this);
    }


    @Override
    public Player getOwner() {
        return this.owner;
    }


    @Override
    public Money getBasicPrice() {
        int townHalfPrice = town.getPattern().getBasicPrice() / 2;
        int improvementPrice = (townHalfPrice + 49)/50 * 50;
        return newMoney(improvementPrice);
    }



    @Override
    public void transfer(Player sender, Player receiver) {
        // player buys improvement from bank
        if (sender instanceof Bank && !(receiver instanceof Bank)) {
            this.owner = receiver;
            this.owner.properties.add(this);
            this.town.getComponents().add(this);
        } // player sells improvement
        else if (!(sender instanceof Bank)) {
            this.owner = null;
            this.town.getComponents().remove(this);
            receiver.properties.add(this.getBasicPrice());
        }

        System.out.println(this.town.getPattern().getName() + " transfered from " + sender.getName() + " to " + receiver.getName() + " game: " + game.getUUID());
    }


    @Override
    public Town getGroup() {
        return this.town;
    }



}
