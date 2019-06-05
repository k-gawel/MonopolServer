package org.california.monopolserver.instance.transferable.discount;

import org.california.monopolserver.instance.utils.AbstractGameInstance;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.model.interfaces.Chargeable;
import org.california.monopolserver.model.interfaces.Transferable;

public abstract class Discount extends AbstractGameInstance implements Transferable {

    private Chargeable chargeable;
    private Player owner;
    private int endTour;

    Discount(Chargeable chargeable, int value, int endTour) {
        super(chargeable.getGame());

        this.chargeable = chargeable;
        this.owner = chargeable.getOwner();
        this.owner.properties.add(this);

        this.game = chargeable.getGame();
        this.endTour = endTour;

        this.setValue(value);
    }

    abstract void setValue(int value);

    public abstract int getValue();
    public abstract Money getCharge();

    public Chargeable getChargeable() {
        return this.chargeable;
    }

    public int getEndTour() {
        return endTour;
    }

    @Override
    public Player getOwner() {
        return this.owner;
    }


    @Override
    public Money getBasicPrice() {
        return new Money(game);
    }


    @Override
    public void transfer(Player sender, Player receiver) {
        this.owner = receiver;
        receiver.properties.add(this);
    }


    public static Discount create(Chargeable chargeable, int value, int endTour, Class<? extends Discount> type) {
        if(type.equals(FixedDiscount.class))
            return new FixedDiscount(chargeable, value, endTour);
        else if(type.equals(PercentageDiscount.class))
            return new PercentageDiscount(chargeable, value, endTour);
        else
            throw new IllegalArgumentException("Wrong type " + type.toString());
    }

}
