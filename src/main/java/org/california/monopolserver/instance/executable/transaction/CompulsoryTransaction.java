package org.california.monopolserver.instance.executable.transaction;

import org.california.monopolserver.exceptions.IncompatibleGamesException;
import org.california.monopolserver.exceptions.NotSideOfTransactionException;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.utils.TransferableCollection;
import org.california.monopolserver.instance.transferable.money.Money;
import org.california.monopolserver.instance.utils.GameInstance;

public class CompulsoryTransaction extends Transaction {

    private Player executor;
    private Player target;

    private Money demand;

    private TransferableCollection executorOffer;
    private TransferableCollection targetOffer;

    private Boolean executorApproval = null;
    private Boolean targetApproval = null;


    public CompulsoryTransaction(Player executor, Player target, Money demand) throws IncompatibleGamesException {
        super(executor, target);
        if(!GameInstance.sameGame(executor, demand))
            throw new IncompatibleGamesException();

        this.executor = executor;
        this.target = target;
        this.demand = demand;

        this.executorOffer = new TransferableCollection(game);
        this.targetOffer = new TransferableCollection(game);
    }


    @Override
    public TransferableCollection getInitiatorOffer() {
        return executorOffer;
    }

    @Override
    public TransferableCollection getInvitedOffer() {
        return targetOffer;
    }

    @Override
    public void setStatus(Player side, boolean status) throws NotSideOfTransactionException {
        if(side.equals(executor))
            executorApproval = status;
        else if (side.equals(target))
            executorApproval = status;
        else
            throw new NotSideOfTransactionException(side, this);
    }


    @Override
    public Boolean getStatus(Player side) {
        if(executor.equals(side)) return executorApproval;
        else if(target.equals(side)) return targetApproval;
        else throw new NotSideOfTransactionException(side, this);
    }

    @Override
    public Player getInitiator() {
        return executor;
    }


    @Override
    public Player getInvited() {
        return target;
    }



    @Override
    public TransferableCollection getOffer(Player side) throws NotSideOfTransactionException {
        if(side.equals(executor))
            return executorOffer;
        else if(side.equals(target))
            return targetOffer;
        else
            throw new NotSideOfTransactionException(side, this);
    }


    public Money getDemand() {
        return this.demand;
    }


}
