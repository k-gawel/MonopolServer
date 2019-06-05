package org.california.monopolserver.instance.transferable.discount;

import org.california.monopolserver.model.interfaces.Chargeable;
import org.california.monopolserver.instance.transferable.money.Money;

public class FixedDiscount extends Discount {

    private int fixedPrice;

    public FixedDiscount(Chargeable chargeable, int fixedPrice, int endTour) {
        super(chargeable, fixedPrice, endTour);
    }


    @Override
    void setValue(int fixedPrice) {
        if(fixedPrice < 0)
            throw new IllegalArgumentException("Fixed price can not be less than zero");

        this.fixedPrice = fixedPrice;
    }


    @Override
    public Money getCharge() {
        return new Money(game, fixedPrice);
    }


    @Override
    public int getValue() {
        return this.fixedPrice;
    }

}
