package org.california.monopolserver.model.interfaces;

import org.california.monopolserver.instance.utils.GameInstance;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.instance.player.Player;


public interface Transferable extends GameInstance {

    Player getOwner();
    Money getBasicPrice();
    void transfer(Player sender, Player receiver);

}
