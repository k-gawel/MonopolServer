package org.california.monopolserver.instance.executable.transaction;

import org.california.monopolserver.exceptions.NotSideOfTransactionException;
import org.california.monopolserver.instance.player.Bank;
import org.california.monopolserver.instance.player.Player;
import org.california.monopolserver.instance.utils.TransferableCollection;
import org.california.monopolserver.model.interfaces.Transferable;

public class BankGift extends Transaction {


    private Bank bank;
    private Player player;

    private TransferableCollection gift;


    public BankGift(Player player, Transferable gift) {
        super(player.getGame().bank, player);

        this.bank = player.getGame().bank;
        this.player = player;
        this.gift = new TransferableCollection(game);
        this.add(bank, gift);
    }


    @Override
    public void setStatus(Player side, boolean status) {
    }


    @Override
    public Boolean getStatus(Player side) {
        return true;
    }

    @Override
    public Bank getInitiator() {
        return this.bank;
    }

    @Override
    public Player getInvited() {
        return this.player;
    }


    @Override
    public TransferableCollection getOffer(Player side) throws NotSideOfTransactionException {
        if(side.equals(player))
            return new TransferableCollection(side);
        else if(side.equals(bank))
            return gift;
        else
            throw new NotSideOfTransactionException(side, this);
    }


}
