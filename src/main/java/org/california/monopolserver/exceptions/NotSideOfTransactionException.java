package org.california.monopolserver.exceptions;

import org.california.monopolserver.instance.executable.transaction.Transaction;
import org.california.monopolserver.instance.player.Player;

public class NotSideOfTransactionException extends GameException {

    public NotSideOfTransactionException(Player player, Transaction transaction) {
        super("Player " + player  + " is no side of T: " + transaction);
    }

}
