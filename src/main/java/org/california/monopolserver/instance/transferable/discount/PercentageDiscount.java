package org.california.monopolserver.instance.transferable.discount;

import org.california.monopolserver.model.interfaces.Chargeable;
import org.california.monopolserver.instance.transferable.money.Money;

public class PercentageDiscount extends Discount {

    private int percentageDiscount;

    public PercentageDiscount(Chargeable chargeable, int percentageDiscount, int endtour) {
        super(chargeable, percentageDiscount, endtour);
    }


    @Override
    void setValue(int percentageDiscount) {
        if(percentageDiscount <= 0 || percentageDiscount > 100)
            throw new IllegalArgumentException("Percentage discount must be in range (0; 100>");

        this.percentageDiscount = percentageDiscount;
    }


    @Override
    public Money getCharge() {
        int basicCharge = getChargeable().getCharge().intValue();
        int newCharge = basicCharge * percentageDiscount / 100;
        return new Money(game, newCharge);
    }


    @Override
    public int getValue() {
        return this.percentageDiscount;
    }


}
