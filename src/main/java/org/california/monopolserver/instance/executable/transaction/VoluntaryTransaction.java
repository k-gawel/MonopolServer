package org.california.monopolserver.instance.executable.transaction;

import org.california.monopolserver.exceptions.NotSideOfTransactionException;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.utils.TransferableCollection;

public class VoluntaryTransaction extends Transaction {


    private Player initiator;
    private Player invited;

    private TransferableCollection initiatorOffer;
    private TransferableCollection invitedOffer;

    private Boolean initiatorApproval = null;
    private Boolean invitedApproval = null;


    public VoluntaryTransaction(Player initiator, Player invited) {
        super(initiator, invited);

        this.initiator = initiator;
        this.invited = invited;

        this.initiatorOffer = new TransferableCollection(game);
        this.invitedOffer = new TransferableCollection(game);
    }

    public VoluntaryTransaction(Transaction transaction) {
        this(transaction.getInitiator(), transaction.getInvited());
    }

    @Override
    public TransferableCollection getInitiatorOffer() {
        return initiatorOffer;
    }

    @Override
    public TransferableCollection getInvitedOffer() {
        return invitedOffer;
    }


    @Override
    public void setStatus(Player side, boolean status) {
        if(side.equals(initiator))
            initiatorApproval = status;
        else if(side.equals(invited))
            invitedApproval = status;
        else
            throw new IllegalArgumentException("Player is not a side of the transaction");
    }

    @Override
    public Boolean getStatus(Player side) {
        if(initiator.equals(side)) return initiatorApproval;
        else if(invited.equals(side)) return invitedApproval;
        else throw new NotSideOfTransactionException(side, this);
    }

    @Override
    public Player getInitiator() {
        return initiator;
    }


    @Override
    public Player getInvited() {
        return invited;
    }


    @Override
    public TransferableCollection getOffer(Player side) throws NotSideOfTransactionException {
        if(side.equals(initiator))
            return initiatorOffer;
        else if(side.equals(invited))
            return invitedOffer;
        else
            throw new NotSideOfTransactionException(side, this);
    }


}
