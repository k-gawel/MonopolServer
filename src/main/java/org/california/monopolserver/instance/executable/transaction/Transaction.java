package org.california.monopolserver.instance.executable.transaction;

import org.california.monopolserver.exceptions.NotSideOfTransactionException;
import org.california.monopolserver.instance.executable.Executable;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.utils.TransferableCollection;
import org.california.monopolserver.instance.utils.AbstractGameInstance;
import org.california.monopolserver.instance.utils.GameInstance;
import org.california.monopolserver.model.interfaces.Transferable;

public abstract class Transaction extends AbstractGameInstance implements Executable {

    public Transaction(Player side1, Player side2) {
        super(side1.getGame());
        if(!GameInstance.sameGame(side1, side2))
            throw new IllegalArgumentException("Both side must be part of the same game");
    }


    public boolean isCompulsory() {
        return this instanceof CompulsoryTransaction;
    }
    public boolean isBank() {
        return this instanceof BankTransaction;
    }
    public boolean isBankGift() {
        return this instanceof BankGift;
    }
    public boolean isVoluntary() {
        return this instanceof VoluntaryTransaction;
    }


    public abstract TransferableCollection getOffer(Player side) throws NotSideOfTransactionException;
    public TransferableCollection getInitiatorOffer() {
        return this.getOffer(getInitiator());
    };
    public TransferableCollection getInvitedOffer() {
        return this.getOffer(getInvited());
    };


    public abstract Boolean getStatus(Player side);
    public Boolean getInitiatorStatus() {
        return getStatus(getInitiator());
    };
    public Boolean getInvitedStatus() {
        return getStatus(getInvited());
    };


    public abstract void setStatus(Player side, boolean status) throws NotSideOfTransactionException;
    public Boolean isAccepted() {
        if(java.lang.Boolean.FALSE.equals(getInitiatorStatus()) || java.lang.Boolean.FALSE.equals(getInvitedStatus()))
            return false;
        else
            return Boolean.TRUE.equals(getInitiatorStatus()) && Boolean.TRUE.equals(getInvitedStatus()) ?
                    true : null;
    }


    public boolean add(Player side, Transferable transferable) {
        return GameInstance.sameGame(side, transferable) && getOffer(side).add(transferable);
    }


    public boolean remove(Player side, Transferable transferable) {
        return getOffer(side).remove(transferable);
    }


    public abstract Player getInitiator();
    public abstract Player getInvited();


    public Player getOppositeSide(Player side) {
        if(side.equals(getInitiator()))
            return getInvited();
        else if(side.equals(getInvited()))
            return getInitiator();
        else
            throw new IllegalArgumentException("Player is not side of the offer");
    }



    @Override
    public String toString() {
        return "[ " + getInitiator() + " - " + getInvited() + " , " + getUUID() + " ] " + this.getClass().getSimpleName();
    }

}
