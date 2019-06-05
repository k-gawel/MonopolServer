package org.california.monopolserver.instance.board.field;

import org.california.monopolserver.model.interfaces.Chargeable;

public class ChargeableField extends Field {

    private Chargeable chargeable;

    public ChargeableField(Chargeable chargeable, int number) {
        super(chargeable.getGame(), number);
        this.chargeable = chargeable;
    }


    @Override
    public Chargeable getLandable() {
        return this.chargeable;
    }


    public boolean isFree() {
        return getLandable().getOwner().isBank();
    }


}
