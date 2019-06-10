package org.california.monopolserver.instance.transferable.utility;

import org.california.monopolserver.instance.utils.AbstractGameInstance;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.model.interfaces.Chargeable;
import org.california.monopolserver.model.interfaces.Groupable;
import org.california.monopolserver.model.interfaces.Landable;
import org.california.monopolserver.model.interfaces.Transferable;
import org.california.monopolserver.model.pattern.landable.utility.UtilityPattern;

public class Utility extends AbstractGameInstance implements Landable, Groupable, Chargeable, Transferable {

    private Player owner;
    private final UtilityRegion region;
    private final UtilityPattern pattern;


    public Utility(UtilityPattern pattern, UtilityRegion region) {
        super(region.getGame());
        this.pattern = pattern;

        this.region = region;
        this.region.addComponent(this);

        this.owner = game.bank;
        this.owner.properties.add(this);
    }


    public UtilityPattern getPattern() {
        return pattern;
    }


    @Override
    public Money getCharge() {
        int charge = pattern.getBasicPrice() / region.getComponents().size() * region.playersUtilities(getOwner());
        return newMoney(charge);
    }


    public static int getChargeOf(int pOwns, int basicCharge) {
        return basicCharge / 4 * pOwns;
    }


    @Override
    public Player getOwner() {
        return this.owner;
    }


    @Override
    public Money getBasicPrice() {
        int price = pattern.getBasicPrice();
        return newMoney(price);
    }



    @Override
    public void transfer(Player sender, Player receiver) {
        if(!sender.properties.remove(this))
            throw new IllegalStateException("Unable to remov [" + this.toString() + "] from [" + sender.toString() + "] properties");

        this.owner = receiver;
        receiver.properties.add(this);

        System.out.println(this.pattern.getName() + " transfered from " + sender.name + " to " + receiver.name + " game: " + game.getUUID());
    }


    @Override
    public UtilityRegion getGroup() {
        return region;
    }


    @Override
    public String toString() {
        return "[ U: " + pattern.getName() + " ]";
    }
}
