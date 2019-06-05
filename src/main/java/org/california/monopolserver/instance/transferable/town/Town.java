package org.california.monopolserver.instance.transferable.town;

import org.california.monopolserver.instance.utils.AbstractGameInstance;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.instance.player.Bank;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.model.interfaces.*;
import org.california.monopolserver.model.pattern.landable.town.TownPattern;

import java.util.Collection;
import java.util.HashSet;

public class Town extends AbstractGameInstance implements Groupable, Chargeable, Compositable, Transferable, Landable {

    private Player owner;
    private TownRegion region;
    private TownPattern pattern;
    private Collection<Improvement> improvements = new HashSet<>();


    public Town(TownPattern pattern, TownRegion region) {
        super(region.getGame());
        this.pattern = pattern;

        this.region = region;
        this.region.addComponent(this);

        this.owner = game.bank;
        this.owner.properties.add(this);
    }


    public TownPattern getPattern() {
        return this.pattern;
    }


    @Override
    public TownRegion getGroup() {
        return this.region;
    }


    @Override
    public Player getOwner() {
        return this.owner;
    }


    @Override
    public Money getBasicPrice() {
        int townPrice = pattern.getBasicPrice();
        int improvementsPrice = this.improvements.stream()
                .map(Improvement::getBasicPrice)
                .mapToInt(Money::intValue)
                .sum();

        return new Money(game, townPrice + improvementsPrice);
    }


    @Override
    public void transfer(Player sender, Player receiver) {

        if(!sender.properties.remove(this))
            throw new IllegalStateException("Unable to remove " + this.toString() + " from " + sender.toString() + " properties");

        this.owner = receiver;
        this.owner.properties.add(this);

        if(receiver instanceof Bank)
            this.improvements.clear();
        else
            this.improvements.forEach(i -> i.owner = receiver);

        System.out.println(this.pattern.getName() + " transfered from " + sender.getName() + " to " + receiver.getName() + " game: " + game.getUUID());
    }


    @Override
    public Money getCharge() {
        int charge;
        int basicPrice = pattern.getBasicPrice();
        int improvementsSize = improvements.size();

        switch (improvementsSize) {
            case 0:
                charge = region.getOwner() == null ? basicPrice / 12 : basicPrice / 8;
                break;
            case 1:
                charge = basicPrice / 3;
                break;
            case 2:
                charge = basicPrice / 2;
                break;
            case 3:
                charge = basicPrice;
                break;
            case 4:
                charge = 2 * basicPrice;
                break;
            case 5:
                charge = 3 * basicPrice;
                break;
            default:
                throw new IllegalStateException("Town has more than 5 improvements");
        }

        return new Money(game, charge);
    }


    @Override
    public Collection<Improvement> getComponents() {
        return this.improvements;
    }


    @Override
    public <T extends Groupable> void addComponent(T improvement) {
        if(!(improvement instanceof Improvement))
            throw new IllegalArgumentException("Component in Town must be instance of Improvement");
        if(improvements.size() >= 5)
            throw new IllegalArgumentException("Town can have max five improvements");

        this.improvements.add((Improvement) improvement);
    }


    @Override
    public String toString() {
        return "[ T: " + pattern.getName() + " ]";
    }
}
