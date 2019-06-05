package org.california.monopolserver.model.interfaces;

import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.instance.player.Player;

public interface Chargeable extends Landable {

    Money getCharge();
    Player getOwner();

}
