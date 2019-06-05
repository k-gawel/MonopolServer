package org.california.monopolserver.instance.executable.transaction;

import org.california.monopolserver.exceptions.NotSideOfTransactionException;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.utils.TransferableCollection;
import org.california.monopolserver.model.interfaces.Transferable;

public class TransactionResult {

    public Transaction transaction;

    public Player initiator;
    public Player invited;

    public TransferableCollection initiatorOffer;
    public TransferableCollection invitedOffer;

    public TransactionResult(Transaction transaction) {
        this.transaction = transaction;
        this.initiator = transaction.getInitiator();
        this.invited = transaction.getInvited();

        this.initiatorOffer = new TransferableCollection(transaction.getGame());
        this.invitedOffer = new TransferableCollection(transaction.getGame());
    }


    public void add(Player side, Transferable item) {
        try {
            this.getOffer(side).add(item);
        } catch (NotSideOfTransactionException e) {
        }
    }


    public TransferableCollection getOffer(Player side) throws NotSideOfTransactionException {
        if(side.equals(initiator))
            return initiatorOffer;
        else if(side.equals(invited))
            return invitedOffer;
        else
            throw new NotSideOfTransactionException(side, transaction);
    }

}
