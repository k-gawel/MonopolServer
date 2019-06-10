package org.california.monopolserver.instance.board.field;

import org.california.monopolserver.instance.executable.transaction.CompulsoryTransaction;
import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.transferable.discount.Discount;
import org.california.monopolserver.instance.transferable.money.Money;
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


    public Transaction land(Player player) {
        getBoard().putPlayer(player, this);
        System.out.println(player + " LANDS ON " + this.number + " CURRENT PLAYER POSITION IS " + player.field());


        return chargeable.getOwner().isBank() ? null : landOnOwnedField(player);
    }


    private Transaction landOnOwnedField(Player player) {
        Money charge = player.properties.getDiscount(getLandable())
                .map(Discount::getCharge)
                .orElse(chargeable.getCharge());

        return new CompulsoryTransaction(chargeable.getOwner(), player, charge);
    }

}
