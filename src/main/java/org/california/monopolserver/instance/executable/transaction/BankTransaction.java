package org.california.monopolserver.instance.executable.transaction;

import org.california.monopolserver.exceptions.NotSideOfTransactionException;
import org.california.monopolserver.instance.player.Bank;
import org.california.monopolserver.instance.utils.TransferableCollection;
import org.california.monopolserver.instance.player.Player;

public class BankTransaction extends Transaction {

    private TransferableCollection bankOffer;
    private TransferableCollection playerOffer;

    private Player player;
    private Bank bank;

    private Boolean playerAcceptance = null;


    public BankTransaction(Player player) {
        super(player, player.getGame().bank);

        this.player = player;
        this.bank = player.getGame().bank;

        this.bankOffer = new TransferableCollection(game);
        this.playerOffer = new TransferableCollection(game);
    }


    @Override
    public void setStatus(Player side, boolean status) throws NotSideOfTransactionException {
        if(side.equals(player))
            playerAcceptance = status;
        else
            throw new NotSideOfTransactionException(side, this);
    }

    @Override
    public Boolean getStatus(Player side) {
        if(player.equals(side)) return playerAcceptance;
        else if(bank.equals(side)) return true;
        else throw new NotSideOfTransactionException(side, this);
    }

    @Override
    public Player getInitiator() {
        return player;
    }

    @Override
    public Bank getInvited() {
        return bank;
    }


    @Override
    public TransferableCollection getOffer(Player side) throws NotSideOfTransactionException {
        if(side.equals(player))
            return playerOffer;
        else if (side.equals(bank))
            return bankOffer;
        else
            throw new NotSideOfTransactionException(side, this);
    }

}
